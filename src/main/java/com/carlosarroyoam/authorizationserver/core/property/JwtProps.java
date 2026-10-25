package com.carlosarroyoam.authorizationserver.core.property;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Propiedades de configuración de los tokens enlazadas desde el prefijo {@code app.jwt}
 * (TTL en milisegundos del código de autorización, el access token y el refresh token).
 */
@Component
@ConfigurationProperties(prefix = "app.jwt")
@Validated
@Getter
@Setter
public class JwtProps {
  @NotNull(message = "app.jwt.authorization-code-ttl-ms must not be null")
  private long authorizationCodeTtlMs;

  @NotNull(message = "app.jwt.access-token-ttl-ms must not be null")
  private long accessTokenTtlMs;

  @NotNull(message = "app.jwt.refresh-token-ttl-ms must not be null")
  private long refreshTokenTtlMs;
}
