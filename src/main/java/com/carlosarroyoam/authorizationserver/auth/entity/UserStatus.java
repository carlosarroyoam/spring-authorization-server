package com.carlosarroyoam.authorizationserver.auth.entity;

/**
 * Estado del ciclo de vida de un {@link User}. Se persiste como cadena en la columna {@code status}
 * y solo el estado {@code ACTIVE} permite autenticarse.
 */
public enum UserStatus {
  ACTIVE,
  INACTIVE,
  DELETED
}
