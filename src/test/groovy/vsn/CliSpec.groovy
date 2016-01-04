package vsn;

import javax.comm.CommPortIdentifier;
import org.apache.commons.cli.*;
import vsn.com.*;
import vsn.comm.*;
import spock.lang.*;

class CliSpec extends Specification {

  CommandLine cli = Mock()
  CommandLineParser parser = Mock() { parse(*_) >> cli }
  HelpFormatter helpFormat = Mock()
  SerialPort serialPort = Mock(SerialPortMock)
  def app = new Cli(cli: cli,
                    helpFormat: helpFormat,
                    parser: parser,
                    serialPort: serialPort)

  def "Las opciones se procesan correctamente"() {
    when:
      cli.hasOption("h") >> true
      app.parse(_ as String[])
    then:
      1 * helpFormat.printHelp(*_)
      0 * serialPort._
    when:
      parser.parse(*_) >> { throw new MissingOptionException("") }
      app.parse(_ as String[])
    then:
      1 * helpFormat.printHelp(*_)
      0 * serialPort._
  }

  def "Conmutar ambos se procesa correctamente"() {
    given:
      cli.getOptionValue("t") >> "both"
    when: "se indica canal de entrada y de salida"
      cli.getOptionValue("o") >> "0"
      cli.getOptionValue("i") >> "0"
      app.parse(_ as String[])
    then:
      1 * serialPort.sendCommand(_, 0, _)
    when: "no se indica canal de entrada"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      cli.getOptionValue("i") >> null
      thrown NumberFormatException
      0 * serialPort.sendCommand(*_)
    when: "no se indica canal de salida"
      cli.getOptionValue("i") >> "0"
      app.parse(_ as String[])
    then:
      cli.getOptionValue("o") >> null
      thrown NumberFormatException
      0 * serialPort.sendCommand(*_)
  }

}
