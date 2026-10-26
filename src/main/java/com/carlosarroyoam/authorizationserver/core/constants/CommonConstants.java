package com.carlosarroyoam.authorizationserver.core.constants;

/**
 * Constantes centralizadas con los mensajes de error usados en las excepciones de la API,
 * reutilizadas entre los distintos servicios para mantener consistencia en las respuestas de error.
 */
public class CommonConstants {
  public static final String ILLEGAL_ACCESS_EXCEPTION = "Illegal access to utility class";

  /** Constructor privado que impide instanciar la clase. */
  private CommonConstants() {
    throw new IllegalAccessError(ILLEGAL_ACCESS_EXCEPTION);
  }
}
