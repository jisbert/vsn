package vsn.com

import spock.lang.*;

class SwitchBothSpec extends Specification {

  @Unroll
  def "SwitchBoth(audio:#audio, output:#output, video:#video) genera #byteArray"() {
    when:
      def command = new SwitchBoth(audio: audio, output: output, video: video)
    then:
      command.getBytes().encodeHex().toString().toUpperCase() == byteArray
    where:
      audio | output | video || byteArray
      null  | 11     | 5     || '42313130350D'
      3     | 4      | 10    || '423034313030330D'
      5     | 7      | 5     || '423037303530350D'
  }

  def "setAudio valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setAudio(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setAudio(17)
    then:
      thrown IllegalArgumentException
    when:
      command.setAudio(null)
    then: "nulo es un valor válido, el canal se determina a partir del de vídeo"
      notThrown Exception
  }

  def "setOutput valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setOutput(null)
    then:
      thrown Exception
    when:
      command.setOutput(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setOutput(17)
    then:
      thrown IllegalArgumentException
  }

  def "setVideo valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setVideo(null)
    then:
      thrown Exception
    when:
      command.setVideo(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setVideo(17)
    then:
      thrown IllegalArgumentException
  }

}
