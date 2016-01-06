package com.vsn.elpro.sdz16.command;

import java.lang.Override;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Comando conmutar vídeo, conmuta sólo el canal de vídeo.
  */
public class SwitchVideo extends AbstractSwitch {

  public static final byte CONTROL = 0x56;
  public static final String INPUT_CHANNEL_TYPE = "vídeo";
  private static final Logger LOGGER =
    LoggerFactory.getLogger(SwitchVideo.class.getName());

  public SwitchVideo() { super(0, 0); }

  public SwitchVideo(int input, int output) { super(input, output); }

  public SwitchVideo(String input, String output) { super(input, output); }

  @Override public byte getControl() { return CONTROL; }

  @Override public String getInputChType() { return INPUT_CHANNEL_TYPE; }

  @Override protected Logger getLogger() { return LOGGER; }

  /** Método de conveniencia para obtener el canal de entrada que en este
    * comando se corresponde con el canal de vídeo.
    * @return identificador numérico del canal de vídeo
    */
  public int getVideo() { return getInput(); }

  /** Método de conveniencia para asignar el canal de entrada que en este
    * comando se corresponde con el canal de vídeo.
    * @param video identificador numérico del canal de vídeo
    */
  public void setVideo(int video) { setInput(video); }

  /** Asigna el canal de vídeo a partir de una secuencia de caracteres.
    * @param video secuencia de caracteres correspondiente al identificador
                   numérico del canal de audio
    * @see #setVideo(int)
    */
  public void setVideo(String video) { setInput(video); }

}
