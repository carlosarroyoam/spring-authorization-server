package com.carlosarroyoam.authorizationserver.core.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenConfig {
  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
      if ("access_token".equals(context.getTokenType().getValue())) {
        List<String> roles = new ArrayList<>();
        context
            .getPrincipal()
            .getAuthorities()
            .forEach(auth -> roles.add(auth.getAuthority().replace("ROLE_", "")));

        context.getClaims().claim("roles", roles);
      }
    };
  }

  @Bean
  TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .authorizationCodeTimeToLive(Duration.ofMinutes(5))
        .accessTokenTimeToLive(Duration.ofMinutes(5))
        .refreshTokenTimeToLive(Duration.ofHours(24))
        .reuseRefreshTokens(true)
        .build();
  }
}
