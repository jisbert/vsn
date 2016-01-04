package vsn.com

import spock.lang.*;

class SwitchBothSpec extends Specification {

  @Unroll
  def "Conmutar o:#output v:#video a:#audio genera #byteArray" () {
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

}
