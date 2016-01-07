package com.vsn.elpro.sdz16.cli;

import com.vsn.elpro.sdz16.command.*;
import java.lang.String;
import java.lang.IllegalStateException;
import java.util.Objects;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Procesa los parámetros.
  */
public class Cli {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(Cli.class.getName());

  private boolean consumed = false;
  private CommandLineParser parser = new DefaultParser();
  private HelpFormatter helpFormat = new HelpFormatter();
  private Options options = new Options();
  private ParameterMap parameterMap = new ParameterMap();

  public Cli() {
    helpFormat.setSyntaxPrefix("uso: ");
    Option audio =    Option.builder("a")
                            .longOpt("audio")
                            .hasArg()
                            .argName("canal")
                            .desc("canal audio de entrada")
                            .build();
    Option help  =    Option.builder("h")
                            .longOpt("help")
                            .desc("muestra este mensaje de ayuda y termina")
                            .build();
    Option input =    Option.builder("i")
                            .longOpt("input")
                            .hasArg()
                            .argName("canal")
                            .desc("canal de entrada, sobrescribe el canal de entrada por defecto, p. e.:\nvsn -type " +
                                  "video -i 10 -v 5\n// canal de entrada 10")
                            .build();
    Option output =   Option.builder("o")
                            .longOpt("output")
                            .hasArg()
                            .argName("canal")
                            .desc("canal de salida")
                            .build();
    Option portName = Option.builder("p")
                            .longOpt("portName")
                            .hasArg()
                            .argName("nombre")
                            .desc("nombre del puerto serie para la comunicación con la matriz")
                            .build();
    Option type =     Option.builder("t")
                            .longOpt("type")
                            .hasArg()
                            .argName("comando")
                            .desc("tipo de comando, los comandos soportados son audio (conmutar audio), both (conmutar" +
                                  " ambos), video (conmutar vídeo)")
                            .required()
                            .build();
    Option video =    Option.builder("v")
                            .longOpt("video")
                            .hasArg()
                            .argName("canal")
                            .desc("canal vídeo de entrada")
                            .build();
    options.addOption(audio)
           .addOption(help)
           .addOption(input)
           .addOption(output)
           .addOption(portName)
           .addOption(type)
           .addOption(video);
  }

  /** Procesa los parámetros y se consume.
    *
    * Para procesar de nuevo los mismos u
    * otros parámetros es necesario instanciar un nuevo objeto.
    * @param args parámetros suministrados en la línea de comandos
    * @return mapa de parámetros procesados
    */
  public ParameterMap parse(String[] args) {
    if (consumed) throw new IllegalStateException("Ya se ha consumido el CLI");
    consumed = true;
    // Procesa las opciones
    CommandLine cli = null;
    try {
      cli = parser.parse(options, args);
    } catch (MissingOptionException e) {
      usage();
      return parameterMap;
    } catch (ParseException e) {
      LOGGER.error("Error al procesar las opciones", e);
      return parameterMap;
    }
    if (cli.hasOption("h")) {
      usage();
      return parameterMap;
    }
    // Configura el mapa de parámetros
    Command command;
    String inputChStr;
    switch (cli.getOptionValue("t")) {
      case "audio":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("a");
        command = new SwitchAudio(inputChStr, cli.getOptionValue("o"));
        break;
      case "both":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("v");
        command = new SwitchBoth(cli.getOptionValue("a"),
                                 cli.getOptionValue("o"),
                                 inputChStr);
        break;
      case "video":
        inputChStr = Objects.nonNull(cli.getOptionValue("i")) ?
                       cli.getOptionValue("i") : cli.getOptionValue("v");
        command = new SwitchVideo(inputChStr, cli.getOptionValue("o"));
        break;
      default:
        usage();
        return parameterMap;
    }
    parameterMap.put(ParameterMap.COMMAND, command);
    parameterMap.put(ParameterMap.PORT_NAME, cli.getOptionValue("p"));
    return parameterMap;
  }

  /** Muestra un mensaje de ayuda.
    */
  public void usage() {
    helpFormat.printHelp("vsn-sdz16-cli -t <comando> [opciones]", options);
  }

}
