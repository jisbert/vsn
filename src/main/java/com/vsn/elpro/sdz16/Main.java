package com.vsn.elpro.sdz16;

import com.vsn.elpro.sdz16.cli.Cli;
import com.vsn.elpro.sdz16.cli.ParameterMap;
import com.vsn.elpro.sdz16.command.Command;
import com.vsn.elpro.sdz16.communications.Session;
import java.lang.String;
import java.util.Objects;
import javax.comm.CommPortIdentifier;

/** Se conecta a la matriz de conmutación elpro SDZ16 y envía un comando por el
  * puerto serie.
  */
public class Main {
  /** Se conecta a la matriz de conmutación elpro SDZ16 y envía un comando por el
    * puerto serie.
    * @param args parámetros suministrados en la línea de comandos
    */
  public static void main(String[] args) {
    ParameterMap parameterMap = new Cli().parse(args);
    Command command = parameterMap.get(ParameterMap.COMMAND, Command.class);
    if (Objects.isNull(command)) return;
    Session session;
    String portName = parameterMap.get(ParameterMap.PORT_NAME, String.class);
    if (Objects.nonNull(portName)) {
      session = new Session(portName);
    } else {
      session = new Session((CommPortIdentifier) null);
    }
    session.request(command);
  }
}
