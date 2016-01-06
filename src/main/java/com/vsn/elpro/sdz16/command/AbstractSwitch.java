package com.vsn.elpro.sdz16.command;

import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.function.IntConsumer;
import java.nio.ByteBuffer;
import org.slf4j.Logger;

public abstract class AbstractSwitch implements Switch {

  protected ByteBuffer buffer = ByteBuffer.allocate(6);
  protected int input = 0;
  protected int output = 0;

  protected AbstractSwitch(int input, int output) {
    setInput(input);
    setOutput(output);
  }

  protected AbstractSwitch(String input, String output) {
    setInput(input);
    setOutput(output);
  }

  /** Asigna el canal de forma segura.
    * @param setChannel función que asigna el canal
    * @param channel identificador numérico del canal
    * @param errorLog mensaje registrado en caso de producirse un error de
    *                 validación, plantilla que soporta el marcador de posición
    *                 '{}' para inyectar el valor del parámetro channel
    */
  protected void secureSetChannel(IntConsumer setChannel, int channel, String errorLog) {
    if (channel < CHANNEL_MIN || CHANNEL_MAX < channel) {
      getLogger().error(errorLog, channel);
      String errorMessage = "El número de canal excede el rango soportado (" +
                            CHANNEL_MIN + " - " + CHANNEL_MAX + ")";
      throw new IllegalArgumentException(errorMessage);
    }
    setChannel.accept(channel);
  }

  /** Asigna el canal a partir de una secuencia de caracteres de forma segura.
    * @param setChannel función que asigna el canal
    * @param channelStr secuencia de caracteres correspondiente al identificador
    *                   numérico del canal
    * @param errorLog mensaje registrado en caso de producirse un error de
    *                 validación, plantilla que soporta el marcador de posición
    *                 '{}' para inyectar el valor del parámetro channelStr
    */
  protected void secureSetChannel(IntConsumer setChannel, String channelStr, String errorLog) {
    try {
      int channel = Integer.parseInt(channelStr);
      secureSetChannel(setChannel, channel, errorLog);
    } catch (NumberFormatException e) {
      getLogger().error(errorLog, channelStr);
      throw new IllegalArgumentException("Formato de canal incorrecto", e);
    }
  }

  public byte[] getBytes() {
    buffer.put(getControl());
    if (output < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(output).getBytes());
    if (input < 10) buffer.put(ZERO);
    buffer.put(String.valueOf(input).getBytes());
    buffer.put(CR);
    buffer.flip();
    byte[] result = new byte[buffer.remaining()];
    buffer.get(result);
    buffer.clear();
    return result;
  }

  /** Obtiene una instancia de {@code Logger} para escribir en el registro
    * @return instancia de Logger para escribir en el registro
    */
  protected abstract Logger getLogger();

  public int getInput() {
    return input;
  }

  private final String inputChErrorLog =
    "Canal de entrada de " + getInputChType() + " incorrecto: {}";

  public void setInput(int input) {
    secureSetChannel(i -> this.input = i, input, inputChErrorLog);
  }

  /** Asigna el canal de entrada a partir de una secuencia de caracteres.
    * @param input secuencia de caracteres correspondiente al identificador
    *              numérico del canal de entrada
    */
  public void setInput(String input) {
    secureSetChannel(i -> this.input = i, input, inputChErrorLog);
  }

  /** Obtiene el tipo de canal de entrada.
    * @return tipo de canal de entrada
    */
  public abstract String getInputChType();

  public int getOutput() {
    return output;
  }

  private final String outputErrorChLog = "Canal de salida incorrecto: {}";

  public void setOutput(int output) {
    secureSetChannel(i -> this.output = i, output, outputErrorChLog);
  }

  /** Asigna el canal de salida a partir de una secuencia de caracteres.
    * @param output secuencia de caracteres correspondiente al identificador
    *              numérico del canal de salida
    */
  public void setOutput(String output) {
    secureSetChannel(i -> this.output = i, output, outputErrorChLog);
  }

}
