package vsn.com;

import java.lang.IllegalArgumentException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class SwitchBoth implements Switch {

  public static final byte CONTROL = 0x42;
  private static final ByteBuffer buffer = ByteBuffer.allocate(8);

  private Integer audio;
  private int output = 0;
  private int video = 0;

  public SwitchBoth() {}

  public SwitchBoth(int input, int output) {
    setOutput(output);
    setInput(input);
  }

  public SwitchBoth(int audio, int output, int video) {
    setAudio(audio);
    setOutput(output);
    setVideo(video);
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

  public byte getControl() {
    return CONTROL;
  }

  public int getInput() {
    return getVideo();
  }

  public void setInput(int input) {
    setVideo(input);
  }

  public int getOutput() {
    return output;
  }

  public void setOutput(int output) {
    if (output < 0 || 16 < output)
      throw new IllegalArgumentException("Canal de salida incorrecto: " + output);
    this.output = output;
  }

  public int getVideo() {
    return video;
  }

  public void setVideo(int video) {
    if (video < 0 || 16 < video)
      throw new IllegalArgumentException("Canal de vídeo incorrecto: " + video);
    this.video = video;
  }

  public byte[] getBytes() {
    buffer.put(CONTROL);
    if (output < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(output).getBytes());
    if (video < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(video).getBytes());
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
