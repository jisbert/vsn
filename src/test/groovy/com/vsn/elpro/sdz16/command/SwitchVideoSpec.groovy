package com.vsn.elpro.sdz16.command

import spock.lang.*

class SwitchVideoSpec extends Specification {

  @Unroll
  def "SwitchVideo(output:#output video:#video) genera #byteArray"() {
    when:
      def command = new SwitchVideo(output: output, video: video)
    then:
      command.getBytes().encodeHex().toString().toUpperCase() == byteArray
    where:
      output | video || byteArray
      15     | 0     || '56313530300D'
  }

}
