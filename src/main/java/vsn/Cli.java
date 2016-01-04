package vsn;

import java.lang.String;
import java.lang.UnsupportedOperationException;
import java.util.Objects;
import org.apache.commons.cli.*;
import vsn.com.*;
import vsn.comm.*;

public class Cli {

  public static void usage(Options options) {
    new HelpFormatter().printHelp("vsn", options);
    System.exit(0);
  }

  public static void main(String[] args) throws ParseException {
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
    Options options = new Options();
    options.addOption(audio)
           .addOption(help)
           .addOption(input)
           .addOption(output)
           .addOption(type)
           .addOption(video);
    // Procesa los argumentos
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cli = null;
    try { cli = parser.parse(options, args); }
    catch (MissingOptionException e) { usage(options); }
    if (cli.hasOption("h")) usage(options);
    String commandType = cli.getOptionValue("t");
    // Inicia el puerto serie
    SerialPort serialPort = new SerialPortMock();
    serialPort.init(null); // TODO: utilizar no nulo con implementación efectiva del puerto serie
    // Envía el comando
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

}
