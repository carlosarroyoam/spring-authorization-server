package com.carlosarroyoam.authorizationserver;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@SpringBootApplication
public class AuthorizationServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }

  @Bean
  CommandLineRunner initClients(
      JdbcRegisteredClientRepository registeredClientRepository,
      PasswordEncoder passwordEncoder,
      TokenSettings tokenSettings) {
    return args -> {
      if (registeredClientRepository.findByClientId("postman-client") == null) {
        RegisteredClient postmanClient =
            RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("postman-client")
                .clientSecret("$2a$12$UV2.X.CBDpj590jLkfGXXuS3qmj5XunygjpaR3X6L5wTYN8JQtGL.")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("openid")
                .scope("profile")
                .scope("email")
                .clientSettings(
                    ClientSettings.builder()
                        .requireAuthorizationConsent(Boolean.TRUE)
                        .requireProofKey(Boolean.FALSE)
                        .build())
                .tokenSettings(tokenSettings)
                .build();

        registeredClientRepository.save(postmanClient);
      }

      if (registeredClientRepository.findByClientId("angular-client") == null) {
        RegisteredClient angularClient =
            RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("angular-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:4200")
                .postLogoutRedirectUri("http://localhost:4200")
                .scope("openid")
                .scope("profile")
                .scope("email")
                .clientSettings(
                    ClientSettings.builder()
                        .requireAuthorizationConsent(Boolean.TRUE)
                        .requireProofKey(Boolean.TRUE)
                        .build())
                .tokenSettings(tokenSettings)
                .build();

        registeredClientRepository.save(angularClient);
      }
    };
  }
}
