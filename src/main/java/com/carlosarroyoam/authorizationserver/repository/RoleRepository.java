package com.carlosarroyoam.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlosarroyoam.authorizationserver.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
