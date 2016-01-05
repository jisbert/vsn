package com.vsn.elpro.sdz16.command;

import java.lang.Override;

/** Comando conmutar audio, conmuta sólo el canal de audio.
  */
public class SwitchAudio extends AbstractSwitch {

  public static final byte CONTROL = 0x41;

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

  @Override public byte getControl() { return CONTROL; }

  @Override public void setInput(int input) { setInput(input, "audio"); }

}
