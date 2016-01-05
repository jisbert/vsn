package com.vsn.elpro.sdz16.communications;

import java.lang.String;
import java.util.Arrays;
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
  * muestra por pantalla y se vuelca al fichero {@code sdz16.log} en la
  * ubicación en la que se ejecuta la aplicación. El fichero rota cada día,
  * creándose un nuevo fichero al terminar el día.
  */
public class SerialPortMock implements SerialPort {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(SerialPortMock.class.getName());

  public int init(CommPortIdentifier port) {
    LOGGER.debug("Conexión con la matriz iniciada");
    return 0;
  }

  public int sendCommand(byte[] buffer, int offset, int length) {
    if (LOGGER.isDebugEnabled()) {
      StringJoiner sj = new StringJoiner(" ");
      for (byte b : buffer) sj.add(String.format("%02X", b));
      String s = sj.toString();
      LOGGER.debug("Enviado commando a la matriz: byte[{}] = {}", buffer.length, s);
    }
    return 0;
  }

  /** Pone un ACK en la primera posición del búffer y el resto de Bytes a 0.
    * @param buffer búffer en el que se almacena la respuesta
    * @return número de Bytes escritos en el búffer, longitud del búffer
    */
  public int getResponse(byte[] buffer) {
    Arrays.fill(buffer, (byte) 0);
    buffer[0] = ACK;
    LOGGER.debug("Obtenida respuesta de la matriz: byte[{}] = {}", buffer.length, buffer);
    return 1;
  }

}
