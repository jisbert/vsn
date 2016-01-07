package com.vsn.elpro.sdz16.communications;

import com.vsn.elpro.sdz16.command.Command;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.Objects;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Sesión de comunicaciones con la matriz.
  */
public class Session {

  /** Tamaño de resupesta máximo en Bytes, corresponde con el tamaño de la
    * respuesta de mayor tamaño obtenida al realizar una solicitud de estado,
    * como indica el apartado 6.5 del manual de usuario.
    */
  public static final int MAX_RESPONSE_SIZE = 74;
  private static final Logger LOGGER =
    LoggerFactory.getLogger(Session.class.getName());

  private final byte[] RESPONSE_BUFFER = new byte[MAX_RESPONSE_SIZE];
  private CommPortIdentifier serialPortId;
  private SerialPort serialPort;

  /** Inicializa la sesión utilizando el identificador de puerto serie con el
    * nombre especificado en cuanto corresponda a un puerto serie.
    * @param portName identificador del puerto serie
    */
  public Session(String portName) {
    serialPort = new SerialPortMock();
    setSerialPortId(portName);
    serialPort.init(serialPortId);
  }

  /** Inicializa la sesión utilizando el identificador de puerto serie con el
    * nombre especificado en cuanto corresponda a un puerto serie.
    * @param serialPort implementación de puerto serie empleada para establecer
    *                   la sesión
    * @param portName identificador del puerto serie
    */
  public Session(SerialPort serialPort, String portName) {
    this.serialPort = serialPort;
    setSerialPortId(portName);
    serialPort.init(serialPortId);
  }

  /** Inicializa la sesión utilizando el identificador de puerto serie.
    * @param serialPortId identificador de puerto serie
    */
  public Session(CommPortIdentifier serialPortId) {
    serialPort = new SerialPortMock();
    this.serialPortId = serialPortId;
    serialPort.init(serialPortId);
  }

  /** Inicializa la sesión utilizando el identificador de puerto serie.
    * @param serialPort implementación de puerto serie empleada para establecer
    *                   la sesión
    * @param serialPortId identificador de puerto serie
    */
  public Session(SerialPort serialPort, CommPortIdentifier serialPortId) {
    this.serialPort = serialPort;
    this.serialPortId = serialPortId;
    serialPort.init(serialPortId);
  }

  /** Obtiene el puerto serie para la comunicación con la matriz.
    * @return puerto serie
    */
  public SerialPort getSerialPort() {
    return serialPort;
  }

  /** Inyecta el puerto serie para la comunicación con la matriz.
    * @param serialPort puerto serie
    */
  public void setSerialPort(SerialPort serialPort) {
    this.serialPort = serialPort;
  }

  /** Obtiene el descriptor del puerto serie para la comunicación con la matriz.
    * @return descriptor del puerto serie
    */
  public CommPortIdentifier getSerialPortId() {
    return serialPortId;
  }

  /** Inyecta el descriptor del puerto serie para la comunicación con la matriz.
    * @param serialPortId descriptor del puerto serie
    */
  public void setSerialPortId(CommPortIdentifier serialPortId) {
    this.serialPortId = serialPortId;
  }

  /** Asigna el identificador de puerto serie con el nombre especificado.
    * @param portName identificador del puerto serie
    */
  public void setSerialPortId(String portName) {
    try {
      CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
        serialPortId = portId;
      else
        LOGGER.warn("El puerto con nombre `{}` no es un puerto serie", portName);
    } catch (NoSuchPortException e) {
      LOGGER.warn("No existe el puerto", e);
    } catch (Throwable t) {
      LOGGER.warn("Error al iniciar el puerto serie", t);
    }
  }

  /** Envía el comando y obtiene una respuesta de la matriz.
    * @param command comando enviado a la matriz
    * @return respuesta obtenida
    */
  public byte[] request(Command command) {
    if (Objects.isNull(command)) return new byte[0];
    final byte[] cmdBytes = command.getBytes();
    final int bytesSent = serialPort.sendCommand(cmdBytes, 0, cmdBytes.length);
    if (cmdBytes.length != bytesSent) {
      LOGGER.error("El número de Bytes enviados no se corresponde");
      return new byte[0];
    }
    int bytesRcvd = serialPort.getResponse(RESPONSE_BUFFER);
    return Arrays.copyOf(RESPONSE_BUFFER, bytesRcvd);
  }

}
