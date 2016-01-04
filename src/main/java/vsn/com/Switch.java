package vsn.com;

import java.lang.Integer;

public interface Switch extends Command {

  int getInput();

  void setInput(int input);

  int getOutput();

  void setOutput(int output);

}
