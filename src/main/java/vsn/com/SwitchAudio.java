package vsn.com;

import java.lang.Override;

public class SwitchAudio extends AbstractSwitch {

  public static final byte CONTROL = 0x41;

  public SwitchAudio() { super(0, 0); }

  public int getAudio() { return getInput(); }

  public void setAudio(int audio) { setInput(audio); }

  @Override public byte getControl() { return CONTROL; }

  @Override public void setInput(int input) { setInput(input, "audio"); }

}
