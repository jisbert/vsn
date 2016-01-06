package com.vsn.elpro.sdz16.command;

import java.lang.Override;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Comando conmutar audio, conmuta sólo el canal de audio.
  */
public class SwitchAudio extends AbstractSwitch {

  public static final byte CONTROL = 0x41;
  public static final String INPUT_CHANNEL_TYPE = "audio";
  private static final Logger LOGGER =
    LoggerFactory.getLogger(SwitchAudio.class.getName());

  public SwitchAudio() { super(0, 0); }

  public SwitchAudio(int input, int output) { super(input, output); }

  public SwitchAudio(String input, String output) { super(input, output); }

  /** Método de conveniencia para obtener el canal de entrada que en este
    * comando se corresponde con el canal de audio.
    * @return identificador numérico del canal de audio
    */
  public int getAudio() { return getInput(); }

  /** Método de conveniencia para asignar el canal de entrada que en este
    * comando se corresponde con el canal de audio.
    * @param audio identificador numérico del canal de audio
    */
  public void setAudio(int audio) { setInput(audio); }

  /** Asigna el canal de audio a partir de una secuencia de caracteres.
    * @param audio secuencia de caracteres correspondiente al identificador
                   numérico del canal de audio
    * @see #setAudio(int)
    */
  public void setAudio(String audio) { setInput(audio); }

  @Override public byte getControl() { return CONTROL; }

  @Override public String getInputChType() { return INPUT_CHANNEL_TYPE; }

  @Override protected Logger getLogger() { return LOGGER; }

}
