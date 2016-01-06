package com.vsn.elpro.sdz16.cli;

import com.vsn.elpro.sdz16.command.SwitchAudio;
import com.vsn.elpro.sdz16.command.SwitchBoth;
import com.vsn.elpro.sdz16.command.SwitchVideo;
import com.vsn.elpro.sdz16.communications.SerialPort;
import com.vsn.elpro.sdz16.communications.SerialPortMock;
import java.lang.*;
import java.util.Enumeration;
import java.util.Objects;
import javax.comm.CommPortIdentifier;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Procesa las opciones y ejecuta el comando. Por defecto utiliza una
  * implementación que suplanta la conexión serie, es posible inyectar una
  * implementación efectiva de la conexión.
  * @see Cli#setSerialPort
  */
public class Cli {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(Cli.class.getName());

  private CommandLine cli = null;
  private CommandLineParser parser = new DefaultParser();
  private CommPortIdentifier serialPortId;
  private HelpFormatter helpFormat = new HelpFormatter();
  private Options options = new Options();
  private SerialPort serialPort = new SerialPortMock();

  public Cli() {
    // Declara el CLI
    Option audio =  Option.builder("a")
                          .longOpt("audio")
                          .hasArg()
                          .argName("canal")
                          .desc("canal audio de entrada")
                          .build();
    Option help  =  Option.builder("h")
                          .longOpt("help")
                          .desc("muestra este mensaje de ayuda y termina")
                          .build();
    Option input =  Option.builder("i")
                          .longOpt("input")
                          .hasArg()
                          .argName("canal")
                          .desc("canal de entrada, sobrescribe el canal de entrada por defecto, p. e.:\nvsn -type " +
                                "video -i 10 -v 5   // canal de entrada 10")
                          .build();
    Option output = Option.builder("o")
                          .longOpt("output")
                          .hasArg()
                          .argName("canal")
                          .desc("canal de salida")
                          .build();
    Option type =   Option.builder("t")
                          .longOpt("type")
                          .hasArg()
                          .argName("comando")
                          .desc("tipo de comando, los comandos soportados son audio (conmutar audio), both (conmutar" +
                                " ambos), video (conmutar vídeo)")
                          .required()
                          .build();
    Option video =  Option.builder("v")
                          .longOpt("video")
                          .hasArg()
                          .argName("canal")
                          .desc("canal vídeo de entrada")
                          .build();
    options.addOption(audio)
           .addOption(help)
           .addOption(input)
           .addOption(output)
           .addOption(type)
           .addOption(video);
    helpFormat.setSyntaxPrefix("uso: ");
  }

  /** Muestra un mensaje de ayuda.
    */
  public void usage() {
    helpFormat.printHelp("vsn-sdz16-cli -t <comando> [opciones]", options);
  }

  /** Obtiene el puerto serie para la comunicación con la matriz.
    * @return puerto serie
    */
  public SerialPort getSerialPort() {
    return serialPort;
  }

  /** Inyecta el puerto serie para la comunicación con la matriz.
    * @param serialPort puerto serie
    */
  public void setSerialPort(SerialPort serialPort) {
    this.serialPort = serialPort;
  }

  /** Obtiene el descriptor del puerto serie para la comunicación con la matriz.
    * Si no se dispone de un descriptor trata de proveer uno.
    * @return descriptor del puerto serie
    */
  public CommPortIdentifier getSerialPortId() {
    if (serialPortId == null) {
      try {
        Enumeration portIdEnum = CommPortIdentifier.getPortIdentifiers();
        while(portIdEnum.hasMoreElements()) {
          CommPortIdentifier portId = (CommPortIdentifier) portIdEnum.nextElement();
          if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            serialPortId = portId;
            break;
          }
        }
      } catch (Throwable t) {
        LOGGER.warn("No se ha obtenido un identificador de puerto válido con javax.comm");
      }
    }
    return serialPortId;
  }

  /** Inyecta el descriptor del puerto serie para la comunicación con la matriz.
    * @param serialPortId descriptor del puerto serie
    */
  public void setSerialPortId(CommPortIdentifier serialPortId) {
    this.serialPortId = serialPortId;
  }

  /** Procesa las opciones y ejecuta el comando.
    * @param args opciones
    */
  public void parse(String[] args) {
    // Procesa las opciones
    try { cli = parser.parse(options, args); }
    catch (MissingOptionException e) { usage(); return; }
    catch (ParseException e) { throw new RuntimeException(e); }
    if (cli.hasOption("h")) { usage(); return;}
    // Inicia la conexión serie
    serialPort.init(getSerialPortId());
    // Configura el comando
    String commandType = cli.getOptionValue("t");
    byte[] commandBytes = null;
    String inputChStr;
    switch (commandType) {
      case "audio":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("a");
        commandBytes = new SwitchAudio(inputChStr, cli.getOptionValue("o")).getBytes();
        break;
      case "both":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("v");
        commandBytes = new SwitchBoth(cli.getOptionValue("a"),
                                      cli.getOptionValue("o"),
                                      inputChStr).getBytes();
        break;
      case "video":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("v");
        commandBytes = new SwitchVideo(inputChStr, cli.getOptionValue("o")).getBytes();
        break;
      default:
        throw new UnsupportedOperationException("Operación no soportada");
    }
    // Ejecuta el comando y obtiene una respuesta
    serialPort.sendCommand(commandBytes, 0, commandBytes.length);
    byte[] response = new byte[1];
    serialPort.getResponse(response);
    if (response[0] == SerialPort.NACK)
      throw new RuntimeException("La operación ha fallado");
  }

  /** Instancia el interfaz y trata de inyectar un puerto serie.
    * @param args argumentos proporcionados al ejecutar la aplicación desde la
    *             línea de comandos
    */
  public static void main(String[] args) {
    new Cli().parse(args);
  }

}
