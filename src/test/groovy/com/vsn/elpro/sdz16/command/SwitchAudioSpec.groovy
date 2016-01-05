package vsn.com

import spock.lang.*;

class SwitchAudioSpec extends Specification {

  @Unroll
  def "SwitchAudio(audio:#audio, output:#output) genera #byteArray"() {
    when:
      def command = new SwitchAudio(audio: audio, output: output)
    then:
      command.getBytes().encodeHex().toString().toUpperCase() == byteArray
    where:
      audio | output || byteArray
      0     | 14     || '41313430300D'
  }

}
