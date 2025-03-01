package com.carlosarroyoam.authorizationserver.service;

import com.carlosarroyoam.authorizationserver.entity.User;
import com.carlosarroyoam.authorizationserver.repository.UserRepository;
import java.util.Map;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

@Service
public class OidcUserInfoService {
  private final UserRepository userRepository;

  public OidcUserInfoService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public OidcUserInfo loadUser(String username) {
    User userByUsername = userRepository.findByUsername(username).orElseThrow(() -> {
      return new UsernameNotFoundException("Username not found: " + username);
    });

    Map<String, Object> claims = OidcUserInfo.builder()
        .subject(username)
        .name(userByUsername.getName())
        .preferredUsername(username)
        .build()
        .getClaims();

    return new OidcUserInfo(claims);
  }
}