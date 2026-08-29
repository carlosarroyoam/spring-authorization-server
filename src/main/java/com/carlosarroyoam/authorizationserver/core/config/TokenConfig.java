package com.carlosarroyoam.authorizationserver.core.config;

import com.carlosarroyoam.authorizationserver.auth.UserRepository;
import com.carlosarroyoam.authorizationserver.auth.entity.Role;
import com.carlosarroyoam.authorizationserver.auth.entity.User;
import com.carlosarroyoam.authorizationserver.core.property.JwtProps;
import java.time.Duration;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * Configuración de la personalización de claims y de los tiempos de vida de los tokens.
 */
@Configuration
public class TokenConfig {
  /**
   * Añade a los JWT emitidos los claims {@code email}, {@code user_id}, {@code name},
   * {@code given_name}, {@code family_name} y {@code roles} del usuario autenticado.
   *
   * @param userRepository repositorio usado para cargar el usuario y sus roles
   * @return el personalizador aplicado a cada contexto de codificación JWT
   */
  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(UserRepository userRepository) {
    return context -> {
      User userByEmail =
          userRepository
              .findByEmail(context.getPrincipal().getName())
              .orElseThrow(() -> new RuntimeException("User not found"));

      context
          .getClaims()
          .claim("email", userByEmail.getEmail())
          .claim("user_id", userByEmail.getId().toString())
          .claim("name", userByEmail.getFirstName() + " " + userByEmail.getLastName())
          .claim("given_name", userByEmail.getFirstName())
          .claim("family_name", userByEmail.getLastName())
          .claim(
              "roles",
              userByEmail.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    };
  }

  /**
   * Define los tiempos de vida del código de autorización, el access token y el refresh token a
   * partir de {@link JwtProps}, sin reutilizar los refresh tokens.
   *
   * @param jwtProps propiedades con los TTL configurados en milisegundos
   * @return la configuración de tokens compartida por los clientes registrados
   */
  @Bean
  TokenSettings tokenSettings(JwtProps jwtProps) {
    return TokenSettings.builder()
        .authorizationCodeTimeToLive(Duration.ofMillis(jwtProps.getAuthorizationCodeTtlMs()))
        .accessTokenTimeToLive(Duration.ofMillis(jwtProps.getAccessTokenTtlMs()))
        .refreshTokenTimeToLive(Duration.ofMillis(jwtProps.getRefreshTokenTtlMs()))
        .reuseRefreshTokens(Boolean.FALSE)
        .build();
  }
}
