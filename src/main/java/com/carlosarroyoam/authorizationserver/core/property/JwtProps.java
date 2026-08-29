package com.carlosarroyoam.authorizationserver.core.property;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades de configuración de los tokens enlazadas desde el prefijo {@code application.jwt}
 * (TTL en milisegundos del código de autorización, el access token y el refresh token).
 */
@Component
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
public class JwtProps {
  @NotNull(message = "authorization-code-ttl-ms must not be null")
  private long authorizationCodeTtlMs;

  @NotNull(message = "access-token-ttl-ms must not be null")
  private long accessTokenTtlMs;

  @NotNull(message = "refresh-token-ttl-ms must not be null")
  private long refreshTokenTtlMs;
}
