package com.carlosarroyoam.authorizationserver.auth;

import com.carlosarroyoam.authorizationserver.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
