package com.vsn.elpro.sdz16.command

import spock.lang.*

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

  def "setAudio anula el canal"() {
    given:
      def command = new SwitchBoth()
      command.setAudio(1);
    when:
      command.setAudio(null);
    then:
      command.getAudio() == null
  }

  def "setAudio anula el canal cuando se pasa como argumento una secuencia vacía"() {
    given:
      def command = new SwitchBoth()
      command.setAudio(1);
    when:
      command.setAudio("");
    then:
      command.getAudio() == null
  }

  def "setAudio valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setAudio(null)
    then: "nulo es un valor válido, el canal se determina a partir del de vídeo"
      notThrown IllegalArgumentException
    when:
      command.setAudio(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setAudio(17)
    then:
      thrown IllegalArgumentException
    when:
      command.setAudio("abc")
    then:
      thrown IllegalArgumentException
    when:
      (Switch.CHANNEL_MIN..Switch.CHANNEL_MAX).each { command.setAudio it }
    then:
      notThrown IllegalArgumentException
  }

  def "setOutput valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setOutput(null)
    then:
      thrown IllegalArgumentException
    when:
      command.setOutput(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setOutput(17)
    then:
      thrown IllegalArgumentException
    when:
      command.setOutput("abc")
    then:
      thrown IllegalArgumentException
    when:
      (Switch.CHANNEL_MIN..Switch.CHANNEL_MAX).each { command.setOutput it }
    then:
      notThrown IllegalArgumentException
  }

  def "setVideo valida el canal"() {
    given:
      def command = new SwitchBoth()
    when:
      command.setVideo(null)
    then:
      thrown IllegalArgumentException
    when:
      command.setVideo(-1)
    then:
      thrown IllegalArgumentException
    when:
      command.setVideo(17)
    then:
      thrown IllegalArgumentException
    when:
      command.setVideo("abc")
    then:
      thrown IllegalArgumentException
    when:
      (Switch.CHANNEL_MIN..Switch.CHANNEL_MAX).each { command.setVideo it }
    then:
      notThrown IllegalArgumentException
  }

}
