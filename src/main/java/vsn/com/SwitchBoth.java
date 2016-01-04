package vsn.com;

import java.lang.IllegalStateException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class SwitchBoth implements Switch {

  public static final byte CONTROL = 0x42;
  private static final ByteBuffer buffer = ByteBuffer.allocate(8);

  private int audio;
  private int output;
  private int video;

  // public SwitchBoth(int output, int video) {
  //   this.output = output;
  //   this.video = video;
  // }
  //
  // public SwitchBoth(int audio, int output, int video) {
  //   this.audio = audio;
  //   this.output = output;
  //   this.video = video;
  // }

  public int getAudio() {
    return audio;
  }

  public void setAudio(int audio) {
    this.audio = audio;
  }

  public byte getControl() {
    return CONTROL;
  }

  public int getInput() {
    return video;
  }

  public void setInput(int input) {
    video = input;
  }

  public int getOutput() {
    return output;
  }

  public void setOutput(int output) {
    this.output = output;
  }

  public int getVideo() {
    return video;
  }

  public void setVideo(int video) {
    this.video = video;
  }

  public byte[] getBytes() {
    if (Objects.isNull(output))
      throw new IllegalStateException("No se ha indicado un canal de salida");
    if (Objects.isNull(audio) && Objects.isNull(video))
      throw new IllegalStateException("No se ha indicado un canal de entrada");
    buffer.put(CONTROL);
    buffer.put(String.valueOf(output).getBytes());
    if (Objects.nonNull(video)) {
      if (video < 10) buffer.put(CERO);
      buffer.put(String.valueOf(video).getBytes());
    }
    if (Objects.nonNull(audio)) {
      if (video < 10) buffer.put(CERO);
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
