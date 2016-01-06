package com.vsn.elpro.sdz16.cli

import com.vsn.elpro.sdz16.command.SwitchAudio
import com.vsn.elpro.sdz16.command.SwitchBoth
import com.vsn.elpro.sdz16.command.SwitchVideo
import com.vsn.elpro.sdz16.communications.SerialPort
import com.vsn.elpro.sdz16.communications.SerialPortMock
import org.apache.commons.cli.*
import spock.lang.*

class CliSpec extends Specification {

  CommandLine cli = Mock()
  CommandLineParser parser = Mock() { parse(*_) >> cli }
  HelpFormatter helpFormat = Mock()
  SerialPort serialPort = Mock(SerialPortMock)
  def app = new Cli(cli: cli,
                    helpFormat: helpFormat,
                    parser: parser,
                    serialPort: serialPort)

  def "Procesa las opciones correctamente"() {
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

  def "Procesa conmutar audio correctamente"() {
    given:
      cli.getOptionValue("t") >> "audio"
    when: "se indica canal de entrada y de salida"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      1 * serialPort.sendCommand([SwitchAudio.CONTROL, 0x30, 0x30, 0x30, 0x30, 0x0D] as byte[], 0, _)
      1 * serialPort.getResponse(_)
  }

  def "Procesa conmutar ambos correctamente"() {
    given:
      cli.getOptionValue("t") >> "both"
    when: "se indica canal de entrada y de salida"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      1 * serialPort.sendCommand([SwitchBoth.CONTROL, 0x30, 0x30, 0x30, 0x30, 0x0D] as byte[], 0, _)
      1 * serialPort.getResponse(_)
    when: "se indica canal de entrada, de salida y de audio"
      cli.getOptionValue("a") >> "0"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      1 * serialPort.sendCommand([SwitchBoth.CONTROL, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x0D] as byte[], 0, _)
      1 * serialPort.getResponse(_)
    when: "no se indica canal de entrada"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      cli.getOptionValue("i") >> null
      thrown IllegalArgumentException
      0 * serialPort.sendCommand(*_)
      0 * serialPort.getResponse(_)
    when: "no se indica canal de salida"
      cli.getOptionValue("i") >> "0"
      app.parse(_ as String[])
    then:
      cli.getOptionValue("o") >> null
      thrown IllegalArgumentException
      0 * serialPort.sendCommand(*_)
      0 * serialPort.getResponse(_)
  }

  def "Procesa conmutar vídeo correctamente"() {
    given:
      cli.getOptionValue("t") >> "video"
    when: "se indica canal de entrada y de salida"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      1 * serialPort.sendCommand([SwitchVideo.CONTROL, 0x30, 0x30, 0x30, 0x30, 0x0D] as byte[], 0, _)
      1 * serialPort.getResponse(_)
  }

  def "Rechaza comandos inválidos"() {
    when:
      cli.getOptionValue("t") >> "inválido"
      app.parse(_ as String[])
    then:
      thrown UnsupportedOperationException
  }

}
