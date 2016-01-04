package vsn.com;

public interface Command {

  static final byte CR = 0x0D;
  static final byte CERO = 0x30;

  byte[] getBytes();

  byte getControl();

}
