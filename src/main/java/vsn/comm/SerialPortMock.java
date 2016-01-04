package vsn.comm;

import java.lang.String;
import java.util.Arrays;
import java.util.StringJoiner;
import javax.comm.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialPortMock implements SerialPort {

  private static final Logger logger =
    LoggerFactory.getLogger(SerialPortMock.class.getName());

  public int init(CommPortIdentifier port) {
    logger.debug("Conexión con la matriz iniciada");
    return 0;
  }

  public int sendCommand(byte[] buffer, int offset, int length) {
    if (logger.isDebugEnabled()) {
      StringJoiner sj = new StringJoiner(" ");
      for (byte b : buffer) sj.add(String.format("%02X", b));
      String s = sj.toString();
      logger.debug("Enviado commando a la matriz: byte[{}] = {}", buffer.length, s);
    }
    return 0;
  }

  public int getResponse(byte[] buffer) {
    Arrays.fill(buffer, (byte) 0);
    buffer[0] = ACK;
    logger.debug("Obtenida respuesta de la matriz: byte[{}] = {}", buffer.length, buffer);
    return 1;
  }

}
