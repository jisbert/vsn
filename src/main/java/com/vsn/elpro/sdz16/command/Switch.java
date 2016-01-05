package com.vsn.elpro.sdz16.command;

/** Interfaz que representa un comando conmutar.
  */
public interface Switch extends Command {

  /** Obtiene el canal de entrada.
    * @return identificador numérico del canal de entrada
    */
  int getInput();

  /** Asigna el canal de entrada.
    * @param input identificador numérico del canal de entrada
    */
  void setInput(int input);

  /** Obtiene el canal de salida.
    * @return identificador numérico del canal de salida
    */
  int getOutput();

  /** Asigna el canal de salida.
    * @param output identificador numérico del canal de salida
    */
  void setOutput(int output);

}
