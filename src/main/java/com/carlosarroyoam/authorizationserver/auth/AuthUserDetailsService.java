package com.carlosarroyoam.authorizationserver.auth;

import com.carlosarroyoam.authorizationserver.auth.entity.User;
import com.carlosarroyoam.authorizationserver.auth.entity.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public AuthUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(this::mapUser)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
  }

  private UserDetails mapUser(User user) {
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPasswordHash())
        .authorities("ROLE_" + user.getRole().getName())
        .disabled(!UserStatus.ACTIVE.equals(user.getStatus()))
        .build();
  }
}
