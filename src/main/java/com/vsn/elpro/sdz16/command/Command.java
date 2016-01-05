package com.vsn.elpro.sdz16.command;

/** Interfaz que representa un comando de la matriz.
  *
  * El conjunto de comandos soportados es extensible mediante este interfaz.
  */
public interface Command {

  /** Retorno de carro en código US-ASCII.
    */
  static final byte CR = 0x0D;
  /** Cero (0) en código US-ASCII.
    */
  static final byte ZERO = 0x30;

  /** Obtiene la representación del comando en secuencia de Bytes atendiendo a
    * la sección 6.0 del manual de usuario.
    * @return secuencia de Bytes
    */
  byte[] getBytes();

  /** Obtiene el carácter de control correspondiente al comando, cada comando
    * tiene asociado un carácter de control único.
    * @return carácter de control
    */
  byte getControl();

}
