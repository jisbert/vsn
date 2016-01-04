package vsn.com;

import java.lang.Integer;

public interface Switch extends Command {

  Integer getOutput();

  void setOutput(Integer output);

}
