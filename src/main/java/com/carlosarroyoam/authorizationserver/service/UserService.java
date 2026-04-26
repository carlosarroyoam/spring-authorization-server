package com.carlosarroyoam.authorizationserver.service;

import com.carlosarroyoam.authorizationserver.entity.User;
import com.carlosarroyoam.authorizationserver.repository.UserRepository;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User userByUsername =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

    return buildUserDetails(userByUsername);
  }

  public User findByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
  }

  private org.springframework.security.core.userdetails.User buildUserDetails(User user) {
    String username = user.getUsername();
    String password = user.getPassword();
    boolean enabled = user.getIsActive();
    boolean accountNonExpired = user.getIsActive();
    boolean credentialsNonExpired = user.getIsActive();
    boolean accountNonLocked = user.getIsActive();
    Collection<? extends GrantedAuthority> authorities =
        Arrays.asList(new SimpleGrantedAuthority(user.getRole().getName()));

    return new org.springframework.security.core.userdetails.User(
        username,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
  }
}
