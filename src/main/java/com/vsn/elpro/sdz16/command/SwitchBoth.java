package com.vsn.elpro.sdz16.command;

import java.nio.ByteBuffer;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Comando conmutar ambos.
  *
  * Conmuta ambos canales, audio y vídeo. Toma como canal de entrada por defecto el
  * canal de vídeo. Si el canal de audio no se indica selecciona el canal con el
  * mismo identificador que el canal de vídeo.
  */
public class SwitchBoth extends SwitchVideo {

  public static final byte CONTROL = 0x42;
  private static final Logger LOGGER =
    LoggerFactory.getLogger(SwitchBoth.class.getName());

  private ByteBuffer buffer = ByteBuffer.allocate(8);
  private Integer audio;

  public SwitchBoth() {}

  public SwitchBoth(Integer audio, int output, int video) {
    setAudio(audio);
    setOutput(output);
    setVideo(video);
  }

  public SwitchBoth(String audio, String output, String video) {
    setAudio(audio);
    setInput(video);
    setOutput(output);
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

  /** Obtiene el canal de audio.
    * @return identificador numérico del canal de audio
    */
  public Integer getAudio() {
    return audio;
  }

  private final String audioInputChErrorLog =
    "Canal de entrada de audio incorrecto: {}";

  /** Asigna el canal de audio.
    *
    * El canal de audio es opcional, si se anula el comando selecciona el canal con
    * el mismo identificador que el canal de entrada por defecto, el canal de vídeo.
    * @param audio identificador numérico del canal de audio
    */
  public void setAudio(Integer audio) {
    if (Objects.isNull(audio)) { this.audio = null; return; }
    secureSetChannel(i -> this.audio = i, audio, audioInputChErrorLog);
  }

  /** Asigna el canal de audio a partir de una secuencia de caracteres.
    * @param audio secuencia de caracteres correspondiente al identificador
                   numérico del canal de audio
    * @see #setAudio(Integer)
    */
  public void setAudio(String audio) {
    if (Objects.isNull(audio) || audio.isEmpty()) { this.audio = null; return; }
    secureSetChannel(i -> this.audio = i, audio, audioInputChErrorLog);
  }

  @Override protected Logger getLogger() { return LOGGER; }

}
