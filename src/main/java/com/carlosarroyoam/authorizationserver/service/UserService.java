package com.carlosarroyoam.authorizationserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carlosarroyoam.authorizationserver.config.security.SecurityUser;
import com.carlosarroyoam.authorizationserver.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loading");
		return userRepository.findByUsername(username).map(SecurityUser::new)
				.orElseThrow(() -> {
					return new UsernameNotFoundException("Username not found: " + username);
				});
	}

	public com.carlosarroyoam.authorizationserver.entity.User findByUsername(String username) {
		com.carlosarroyoam.authorizationserver.entity.User userByUsername = userRepository.findByUsername(username)
				.orElseThrow(() -> {
					return new UsernameNotFoundException("Username not found: " + username);
				});

		return userByUsername;
	}
}
