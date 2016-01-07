package com.vsn.elpro.sdz16.cli

import com.vsn.elpro.sdz16.command.SwitchAudio
import com.vsn.elpro.sdz16.command.SwitchBoth
import com.vsn.elpro.sdz16.command.SwitchVideo
import org.apache.commons.cli.*
import spock.lang.*

class CliSpec extends Specification {

  CommandLine cli = Mock()
  CommandLineParser parser = Mock() { parse(*_) >> cli }
  HelpFormatter helpFormat = Mock()
  ParameterMap parameterMap = Mock()
  def app = new Cli(helpFormat: helpFormat,
                    parser: parser,
                    parameterMap: parameterMap)

  def "Sólo puede utilizarse una vez"() {
    when:
      cli.hasOption("h") >> true
      app.parse(_ as String[])
      app.parse(_ as String[])
    then:
      thrown IllegalStateException
      0 * parameterMap._
  }

  def "vsn-sdz16-cli"() {
    when:
      parser.parse(*_) >> { throw new MissingOptionException("") }
      app.parse(_ as String[])
    then:
      1 * helpFormat._
      0 * parameterMap._
  }

  def "vsn-sdz16-cli -h"() {
    when:
      cli.hasOption("h") >> true
      app.parse(_ as String[])
    then:
      1 * helpFormat._
      0 * parameterMap._
  }

  def "vsn-sdz16-cli -t invalid"() {
    when:
      cli.getOptionValue("t") >> "invalid"
      app.parse(_ as String[])
    then:
      1 * helpFormat._
      0 * parameterMap._
  }

  def "vsn-sdz16-cli -t audio -a 0 -o 0"() {
    when:
      cli.getOptionValue("t") >> "audio"
      cli.getOptionValue("a") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      0 * helpFormat._
      1 * parameterMap.put(ParameterMap.COMMAND, _ as SwitchAudio)
  }

  def "vsn-sdz16-cli -t both -i 0 -o 0"() {
    when:
      cli.getOptionValue("t") >> "both"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      0 * helpFormat._
      1 * parameterMap.put(ParameterMap.COMMAND, _ as SwitchBoth)
  }

  def "vsn-sdz16-cli -t video -v 0 -o 0"() {
    when:
      cli.getOptionValue("t") >> "video"
      cli.getOptionValue("v") >> "0"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      0 * helpFormat._
      1 * parameterMap.put(ParameterMap.COMMAND, _ as SwitchVideo)
  }

  def "vsn-sdz16-cli -t audio -a 0 -o 0 -p a"() {
    when:
      cli.getOptionValue("t") >> "audio"
      cli.getOptionValue("a") >> "0"
      cli.getOptionValue("o") >> "0"
      cli.getOptionValue("p") >> "a"
      app.parse(_ as String[])
    then:
      0 * helpFormat._
      1 * parameterMap.put(ParameterMap.PORT_NAME, _ as String)
  }

  def "vsn-sdz16-cli -t both -i 0 -o 0 -v 10"() {
    when:
      cli.getOptionValue("t") >> "both"
      cli.getOptionValue("i") >> "0"
      cli.getOptionValue("v") >> "10"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      0 * helpFormat._
      1 * parameterMap.put(ParameterMap.COMMAND, { it.input == 0 })
  }

  def "vsn-sdz16-cli -t video -o 0"() {
    when:
      cli.getOptionValue("t") >> "video"
      cli.getOptionValue("o") >> "0"
      app.parse(_ as String[])
    then:
      thrown IllegalArgumentException
      0 * parameterMap._
  }

}
