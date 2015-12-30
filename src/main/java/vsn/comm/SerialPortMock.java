package vsn.comm;

import javax.comm.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialPortMock implements SerialPort {

  private static final Logger logger =
    LoggerFactory.getLogger(SerialPortMock.class.getName());

  public int init(CommPortIdentifier port) {
    logger.debug("Iniciando conexión con la matriz...");
    logger.debug("...hecho");
    return 0;
  }

  public int sendCommand(byte[] buffer, int offset, int length) {
    logger.debug("Enviando comando a la matriz...");
    logger.debug("...hecho");
    return 0;
  }

  public int getResponse(byte[] buffer) {
    logger.debug("Obteniendo respuesta de la matriz...");
    logger.debug("...hecho");
    return 0;
  }

}
