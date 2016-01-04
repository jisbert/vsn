package vsn.comm;

import java.util.Arrays;
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
    logger.debug("Enviado commando a la matriz: byte[{}] = {}", length, buffer);
    return 0;
  }

  public int getResponse(byte[] buffer) {
    Arrays.fill(buffer, (byte) 0);
    buffer[0] = ACK;
    logger.debug("Obtenida respuesta de la matriz: {}", buffer);
    return 1;
  }

}
