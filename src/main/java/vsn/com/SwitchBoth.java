package vsn.com;

import java.lang.IllegalStateException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class SwitchBoth implements Switch {

  public static final byte CONTROL = 0x42;
  private static final ByteBuffer buffer = ByteBuffer.allocate(8);

  private Integer audio;
  private Integer output;
  private Integer video;

  public SwitchBoth() {}

  public SwitchBoth(Integer input, Integer output) {
    this.output = output;
    this.video = video;
  }

  public SwitchBoth(Integer audio, Integer output, Integer video) {
    this.audio = audio;
    this.output = output;
    this.video = video;
  }

  public Integer getAudio() {
    return audio;
  }

  public void setAudio(Integer audio) {
    this.audio = audio;
  }

  public byte getControl() {
    return CONTROL;
  }

  public Integer getInput() {
    return video;
  }

  public void setInput(Integer input) {
    video = input;
  }

  public Integer getOutput() {
    return output;
  }

  public void setOutput(Integer output) {
    this.output = output;
  }

  public Integer getVideo() {
    return video;
  }

  public void setVideo(Integer video) {
    this.video = video;
  }

  public byte[] getBytes() {
    if (Objects.isNull(output))
      throw new IllegalStateException("No se ha indicado un canal de salida");
    if (Objects.isNull(video))
      throw new IllegalStateException("No se ha indicado un canal de entrada");
    buffer.put(CONTROL);
    if (output < 10) buffer.put(CERO);
    buffer.put(String.valueOf(output).getBytes());
    if (video < 10) buffer.put(CERO);
    buffer.put(String.valueOf(video).getBytes());
    if (Objects.nonNull(audio)) {
      if (audio < 10) buffer.put(CERO);
      buffer.put(String.valueOf(audio).getBytes());
    }
    buffer.put(CR);
    buffer.flip();
    byte[] result = new byte[buffer.remaining()];
    buffer.get(result);
    buffer.clear();
    return result;
  }

}
