package com.carlosarroyoam.authorizationserver.repository;

import com.carlosarroyoam.authorizationserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
