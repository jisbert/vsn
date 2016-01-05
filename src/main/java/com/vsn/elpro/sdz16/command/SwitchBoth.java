package vsn.com;

import java.lang.IllegalArgumentException;
import java.nio.ByteBuffer;
import java.util.Objects;

/** Comando conmutar ambos.
  *
  * Conmuta ambos canales, audio y vídeo. Toma como canal de entrada por defecto el
  * canal de vídeo. Si el canal de audio no se indica selecciona el canal con el
  * mismo identificador que el canal de vídeo.
  */
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

  /** Obtiene el canal de audio.
    * @return identificador numérico del canal de audio
    */
  public int getAudio() {
    return audio;
  }

  /** Asigna el canal de audio.
    *
    * El canal de audio es opcional, si se anula el comando selecciona el canal con
    * el mismo identificador que el canal de entrada por defecto, el canal de vídeo.
    * @param audio identificador numérico del canal de audio
    */
  public void setAudio(Integer audio) {
    if (Objects.isNull(audio)) return;
    if (audio < 0 || 16 < audio)
      throw new IllegalArgumentException("Canal de entrada de audio incorrecto: " + audio);
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
