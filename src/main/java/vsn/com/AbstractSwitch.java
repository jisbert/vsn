package vsn.com;

import java.lang.IllegalArgumentException;
import java.nio.ByteBuffer;

public abstract class AbstractSwitch implements Switch {

  protected ByteBuffer buffer = ByteBuffer.allocate(6);
  protected int input = 0;
  protected int output = 0;

  protected AbstractSwitch(int input, int output) {
    setInput(input);
    setOutput(output);
  }

  protected AbstractSwitch(String input, String output) {
    setInput(Integer.parseInt(input));
    setOutput(Integer.parseInt(output));
  }

  public int getInput() {
    return input;
  }

  protected void setInput(Integer input, String nombre) {
    if (input < 0 || 16 < input)
      throw new IllegalArgumentException("Canal de entrada de " + nombre + " incorrecto: " + input);
    this.input = input;
  }

  public int getOutput() {
    return output;
  }

  public void setOutput(int output) {
    if (output < 0 || 16 < output)
      throw new IllegalArgumentException("Canal de salida incorrecto: " + output);
    this.output = output;
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

}
