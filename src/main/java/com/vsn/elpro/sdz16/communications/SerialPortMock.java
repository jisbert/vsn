package com.vsn.elpro.sdz16.communications;

import java.lang.String;
import java.util.Objects;
import java.util.StringJoiner;
import javax.comm.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implementación que suplanta una conexión real.
  *
  * Esta implementación no inicia sesión alguna con la matriz ni envía ningún
  * comando. No obstante, utiliza <a href="http://logback.qos.ch/">logback</a>
  * para crear un registro de las interacciones que se producen. La
  * configuración de logback se proporciona en el archivo {@code logback.xml} y
  * determina el modo en que se crea el registro. Por defecto, el registro se
  * muestra por pantalla y se vuelca al fichero {@code vsn-sdz16-cli.log} en la
  * ubicación en la que se ejecuta la aplicación. El fichero rota cuando supera
  * un tamaño de 5 MB, creándose un nuevo fichero al superar este tamaño.
  */
public class SerialPortMock implements SerialPort {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(SerialPortMock.class.getName());

  public int init(CommPortIdentifier port) {
    if (LOGGER.isDebugEnabled()) {
      String name = Objects.nonNull(port) ? port.getName() : null;
      LOGGER.debug("Conexión con la matriz iniciada en el puerto serie: {}", name);
    }
    return 0;
  }

  public int sendCommand(byte[] buffer, int offset, int length) {
    if (LOGGER.isDebugEnabled()) {
      StringJoiner sj = new StringJoiner(" ");
      for (byte b : buffer) sj.add(String.format("%02X", b));
      String s = sj.toString();
      LOGGER.debug("Enviado commando a la matriz: byte[{}] = {}", buffer.length, s);
    }
    return length;
  }

  public int getResponse(byte[] buffer) {
    buffer[0] = ACK;
    LOGGER.debug("Obtenida respuesta de la matriz: byte[{}] = {}",
                 1, String.format("%02X", buffer[0]));
    return 1;
  }

}
