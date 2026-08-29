package com.carlosarroyoam.authorizationserver.core.config;

import com.carlosarroyoam.authorizationserver.core.property.CorsProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de seguridad web. Define una cadena de filtros prioritaria para los endpoints
 * OAuth2/OIDC, otra por defecto para el resto de peticiones y la fuente de configuración CORS.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  /**
   * Cadena de filtros prioritaria que aplica solo a las rutas del protocolo OAuth2, habilita OpenID
   * Connect y redirige a {@code /login} las peticiones HTML no autenticadas.
   *
   * @param http constructor de la configuración de seguridad
   * @param corsConfigurationSource fuente de configuración CORS inyectada por Spring
   * @return la cadena de filtros construida
   * @throws Exception si falla la construcción de la configuración
   */
  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(
      HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        OAuth2AuthorizationServerConfigurer.authorizationServer();

    http.csrf(CsrfConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .sessionManagement(
            sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .with(authorizationServerConfigurer, auth -> auth.oidc(Customizer.withDefaults()))
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .exceptionHandling(
            ex ->
                ex.defaultAuthenticationEntryPointFor(
                    new LoginUrlAuthenticationEntryPoint("/login"),
                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));

    return http.build();
  }

  /**
   * Cadena de filtros por defecto: habilita CORS y el inicio de sesión por formulario, deja
   * públicas {@code /login} y {@code /error} y exige autenticación para el resto.
   *
   * @param http constructor de la configuración de seguridad
   * @return la cadena de filtros construida
   * @throws Exception si falla la construcción de la configuración
   */
  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults()).formLogin(Customizer.withDefaults());

    http.authorizeHttpRequests(
        authorize ->
            authorize
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/error")
                .permitAll()
                .anyRequest()
                .authenticated());

    return http.build();
  }

  /**
   * Construye la fuente de configuración CORS para todas las rutas a partir de los valores
   * definidos en {@link CorsProps}.
   *
   * @param corsProps propiedades CORS enlazadas desde la configuración de la aplicación
   * @return la fuente de configuración CORS
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource(CorsProps corsProps) {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsProps.getAllowedOrigins());
    configuration.setAllowedMethods(corsProps.getAllowedMethods());
    configuration.setAllowedHeaders(corsProps.getAllowedHeaders());
    configuration.setExposedHeaders(corsProps.getExposedHeaders());
    configuration.setAllowCredentials(corsProps.getAllowCredentials());

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
