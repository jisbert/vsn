package com.vsn.elpro.sdz16.communications;

import javax.comm.CommPortIdentifier;

/** Puerto serie para la comunicación con la matriz.
  */
public interface SerialPort {

  /** Constante que indica que la operación se ha completado correctamente.
    */
  static final byte ACK = 0x06;

  /** Constante que indica que la operación ha fallado.
    */
  static final byte NACK = 0x15;

  /** Inicia la sesión con la matriz.
    * @param port descriptor del puerto serie
    * @return resultado del establecimiento de conexión
    */
  int init(CommPortIdentifier port);

  /** Envía un comando a la matríz.
    * @param buffer secuencia de Bytes que representa el comando atiende a la
    *               sección 6.0 del manual de usuario
    * @param offset posición del primer Byte relevante
    * @param length longitud de la secuencia de Bytes
    * @return número de Bytes enviados
    */
  int sendCommand(byte[] buffer, int offset, int length);

  /** Obtiene de la matriz la respuesta al último comando enviado.
    * @param buffer búffer en el que se almacena la respuesta
    * @return número de Bytes escritos en el búffer
    */
  int getResponse(byte[] buffer);

}
