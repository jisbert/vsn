package com.vsn.elpro.sdz16.cli;

import java.lang.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Mapa de parámetros procesados.
  */
public class ParameterMap {

  /** Identificador del parámetro {@code COMMAND}.
    */
  public static final int COMMAND = 0;
  /** Identificador del parámetro {@code PORT_NAME}.
    */
  public static final int PORT_NAME = 1;
  private static final Logger LOGGER =
    LoggerFactory.getLogger(ParameterMap.class.getName());
  private static final Map<Integer, Object> parameterMap = new HashMap<>();

  /** Introduce un parámetro en el mapa.
    * @param parameter identificador del parámetro
    * @param value valor introducido
    */
  public void put(Integer parameter, Object value) {
    parameterMap.put(parameter, value);
  }

  /** Obtiene un parámetro del mapa.
    * @param <T> tipo de parámetro
    * @param parameter identificador del parámetro
    * @param clazz tipo de parámetro
    * @return valor obtenido
    */
  public <T> T get(Integer parameter, Class<T> clazz) {
    Object rawValue = parameterMap.get(parameter);
    return clazz.cast(rawValue);
  }

}
