package vsn.com;

import java.lang.Override;

/** Comando conmutar vídeo, conmuta sólo el canal de vídeo.
  */
public class SwitchVideo extends AbstractSwitch {

  public static final byte CONTROL = 0x56;

  public SwitchVideo() { super(0, 0); }

  public SwitchVideo(int input, int output) { super(input, output); }

  public SwitchVideo(String input, String output) { super(input, output); }

  @Override public byte getControl() { return CONTROL; }

  @Override public void setInput(int input) { setInput(input, "vídeo"); };

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

}
