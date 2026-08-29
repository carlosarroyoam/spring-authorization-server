package com.carlosarroyoam.authorizationserver.core.property;

import jakarta.validation.constraints.NotNull;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades con el par de claves RSA (pública y privada) enlazadas desde el prefijo {@code
 * application.rsa}, usadas para firmar y verificar los JWT.
 */
@Component
@ConfigurationProperties(prefix = "application.rsa")
@Getter
@Setter
public class RsaKeyProps {
  @NotNull(message = "private-key must not be null")
  private RSAPrivateKey privateKey;

  @NotNull(message = "public-key must not be null")
  private RSAPublicKey publicKey;
}
