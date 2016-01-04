package vsn;

import java.lang.RuntimeException;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import java.util.Objects;
import javax.comm.CommPortIdentifier;
import org.apache.commons.cli.*;
import vsn.com.Command;
import vsn.com.SwitchBoth;
import vsn.comm.SerialPort;
import vsn.comm.SerialPortMock;

public class Cli {

  private CommandLine cli = null;
  private CommandLineParser parser;
  private CommPortIdentifier serialPortId;
  private HelpFormatter helpFormat;
  private Options options = new Options();
  private SerialPort serialPort;

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
                          .desc("tipo de comando, los comandos soportados son both (conmutar ambos), audio (conmutar " +
                                "audio), video (conmutar vídeo)")
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
    parser = new DefaultParser();
    helpFormat = new HelpFormatter();
    serialPort = new SerialPortMock();
  }

  public void usage() {
    helpFormat.printHelp("vsn", options);
    System.exit(0);
  }

  public void parse(String[] args) {
    // Procesa los argumentos
    try { cli = parser.parse(options, args); }
    catch (MissingOptionException e) { usage(); }
    catch (ParseException e) { throw new RuntimeException(e); }
    if (cli.hasOption("h")) usage();
    // Inicia la conexión serie
    serialPort.init(serialPortId);
    String commandType = cli.getOptionValue("t");
    switch (commandType) {
      case "audio":
        break;
      case "both":
        int audioCh = Objects.nonNull(cli.getOptionValue("a")) ?
                        Integer.parseInt(cli.getOptionValue("a")) : null;
        System.out.println(cli.getOptionValue("o"));
        int outputCh = Integer.parseInt(cli.getOptionValue("o"));
        String inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                              cli.getOptionValue("i") : cli.getOptionValue("v");
        int inputCh  = Integer.parseInt(inputChStr);
        Command command = new SwitchBoth(audioCh, outputCh, inputCh);
        byte[] byteArray = command.getBytes();
        serialPort.sendCommand(byteArray, 0, byteArray.length);
        break;
      case "video":
        break;
      default:
        throw new UnsupportedOperationException("Operación no soportada");
    }
  }

  public static void main(String[] args) {
    new Cli().parse(args);
  }

}
