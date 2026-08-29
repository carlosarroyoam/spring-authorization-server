package com.carlosarroyoam.authorizationserver.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

/**
 * Configuración de los componentes del servidor de autorización OAuth2: settings del servidor,
 * implementaciones JDBC de clientes, autorizaciones y consentimientos, codificador de contraseñas y
 * proveedor de autenticación.
 */
@Configuration
public class AuthorizationServerConfig {
  /**
   * Configuración global del servidor de autorización.
   *
   * @param issuer valor del emisor ({@code iss}) leído de {@code application.auth.server.issuer}
   * @return la configuración con el emisor establecido
   */
  @Bean
  AuthorizationServerSettings authorizationServerSettings(
      @Value("${application.auth.server.issuer}") String issuer) {
    return AuthorizationServerSettings.builder().issuer(issuer).build();
  }

  /**
   * Repositorio de clientes OAuth2 registrados, respaldado por base de datos vía JDBC.
   *
   * @param jdbcTemplate plantilla JDBC hacia el datasource de la aplicación
   * @return el repositorio de clientes
   */
  @Bean
  RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    return new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  /**
   * Servicio que persiste las autorizaciones OAuth2 (códigos y tokens emitidos) vía JDBC.
   *
   * @param jdbcTemplate plantilla JDBC hacia el datasource de la aplicación
   * @param registeredClientRepository repositorio usado para resolver el cliente de cada
   *     autorización
   * @return el servicio de autorizaciones
   */
  @Bean
  OAuth2AuthorizationService authorizationService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
  }

  /**
   * Servicio que persiste los consentimientos otorgados por los usuarios a los clientes vía JDBC.
   *
   * @param jdbcTemplate plantilla JDBC hacia el datasource de la aplicación
   * @param registeredClientRepository repositorio usado para resolver el cliente de cada
   *     consentimiento
   * @return el servicio de consentimientos
   */
  @Bean
  OAuth2AuthorizationConsentService authorizationConsentService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
  }

  /**
   * Codificador de contraseñas basado en BCrypt con factor de coste 12.
   *
   * @return el codificador usado para cifrar y verificar contraseñas y secretos de cliente
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  /**
   * Proveedor de autenticación que valida las credenciales de usuario combinando el {@link
   * UserDetailsService} de la aplicación con el {@link PasswordEncoder} BCrypt.
   *
   * @param userDetailsService servicio que carga los usuarios por su correo
   * @param passwordEncoder codificador usado para comparar la contraseña recibida con la almacenada
   * @return el proveedor de autenticación configurado
   */
  @Bean
  DaoAuthenticationProvider authProvider(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider =
        new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return authenticationProvider;
  }
}
