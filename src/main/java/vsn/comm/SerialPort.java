package vsn.comm;

import javax.comm.CommPortIdentifier;

public interface SerialPort {

  int init(CommPortIdentifier port);

  int sendCommand(byte[] buffer, int offset, int length);

  int getResponse(byte[] buffer);

}
