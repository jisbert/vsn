package vsn.com;

import java.lang.IllegalArgumentException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class SwitchBoth extends SwitchVideo {

  public static final byte CONTROL = 0x42;

  private ByteBuffer buffer = ByteBuffer.allocate(8);
  private Integer audio;

  public SwitchBoth() {}

  public SwitchBoth(Integer audio, int output, int video) {
    setAudio(audio);
    setOutput(output);
    setVideo(video);
  }

  public SwitchBoth(String audio, String output, String video) {
    if (Objects.nonNull(audio)) setAudio(Integer.parseInt(audio));
    setOutput(Integer.parseInt(output));
    setVideo(Integer.parseInt(video));
  }

  public int getAudio() {
    return audio;
  }

  public void setAudio(Integer audio) {
    if (Objects.isNull(audio)) return;
    if (audio < 0 || 16 < audio)
      throw new IllegalArgumentException("Canal de audio incorrecto: " + audio);
    this.audio = audio;
  }

  @Override public byte[] getBytes() {
    buffer.put(CONTROL);
    if (output < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(output).getBytes());
    if (input < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(input).getBytes());
    if (Objects.nonNull(audio)) {
      if (audio < 10) buffer.put(ZERO);
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
