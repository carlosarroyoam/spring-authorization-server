package com.carlosarroyoam.authorizationserver.auth;

import com.carlosarroyoam.authorizationserver.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA para la entidad {@link Role} (tabla {@code roles}). */
public interface RoleRepository extends JpaRepository<Role, Integer> {}
