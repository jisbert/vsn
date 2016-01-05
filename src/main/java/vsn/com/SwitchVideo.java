package vsn.com;

import java.lang.Override;

public class SwitchVideo extends AbstractSwitch {

  public static final byte CONTROL = 0x56;

  public SwitchVideo() { super(0, 0); }

  @Override public byte getControl() { return CONTROL; }

  @Override public void setInput(int input) { setInput(input, "vídeo"); };

  public int getVideo() { return getInput(); }

  public void setVideo(int video) { setInput(video); }

}
