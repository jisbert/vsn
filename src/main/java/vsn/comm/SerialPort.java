package vsn.comm;

import javax.comm.CommPortIdentifier;

/** Puerto serie para la comunicación con la matriz.
  */
public interface SerialPort {

  /** Constante que indica que la operación se ha completado correctamente.
    */
  static final byte ACK = 0x06;

  /** Constante que indica que la operación no se ha podido completar
    * correctamente.
    */
  static final byte NACK = 0x15;

  /** Inicia la sesión con la matriz.
    * @param port descriptor del puerto serie en el que se indican sus
    *             características
    * @return resultado del establecimiento de conexión especificado en el
    *         manual de usuario
    */
  int init(CommPortIdentifier port);

  /** Envía un comando a la matríz.
    * @param buffer secuencia de Bytes que representa el comando, corresponde a
    *               aplicar el código especificado en la sección x.x del manual
    *               del manual de usuario
    * @param offset ...
    * @param length ...
    * @return número de Bytes enviados
    */
  int sendCommand(byte[] buffer, int offset, int length);

  /** Obtiene de la matriz la respuesta al último comando enviado.
    * @param buffer ...
    * @return número de Bytes recibidos
    */
  int getResponse(byte[] buffer);

}
