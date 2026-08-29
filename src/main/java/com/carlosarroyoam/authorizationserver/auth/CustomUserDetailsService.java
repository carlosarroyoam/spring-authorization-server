package com.carlosarroyoam.authorizationserver.auth;

import com.carlosarroyoam.authorizationserver.auth.entity.User;
import com.carlosarroyoam.authorizationserver.auth.entity.UserStatus;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de {@link UserDetailsService} que carga los usuarios desde la base de datos usando
 * el correo electrónico como nombre de usuario.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Carga un usuario por su correo electrónico y lo adapta al modelo de Spring Security. La
   * consulta se ejecuta en una transacción de solo lectura para inicializar los roles.
   *
   * @param email correo electrónico usado como nombre de usuario
   * @return los datos del usuario ({@link UserDetails}) para la autenticación
   * @throws UsernameNotFoundException si no existe ningún usuario con ese correo
   */
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(this::mapUser)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
  }

  /**
   * Convierte la entidad {@link User} en un {@link UserDetails}. Cada rol pasa a ser una autoridad
   * con prefijo {@code ROLE_} y el usuario se deshabilita si su estado no es {@code ACTIVE}.
   *
   * @param user entidad de usuario obtenida del repositorio
   * @return los datos de autenticación equivalentes
   */
  private UserDetails mapUser(User user) {
    Set<SimpleGrantedAuthority> autorities =
        user.getRoles().stream()
            .map(role -> "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPasswordHash())
        .authorities(autorities)
        .disabled(!UserStatus.ACTIVE.equals(user.getStatus()))
        .build();
  }
}
