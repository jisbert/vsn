package vsn.com

import spock.lang.*;

class SwitchBothSpec extends Specification {

  // def "Conmutar #output #video #audio genera #byteArray" () {
  @Unroll
  def "Conmutar #output #video genera #byteArray" () {
    when:
      // def command = new SwitchBoth(audio: audio, output: output, video: video)
      def command = new SwitchBoth(output: output, video: video)
    then:
      command.getBytes().encodeHex().toString().toUpperCase() == byteArray
    where:
    // output | video | audio || byteArray
    // 11     | 5     | 0     || '42313130350D'
      output | video || byteArray
      11     | 5     || '42313130350D'
  }

}
