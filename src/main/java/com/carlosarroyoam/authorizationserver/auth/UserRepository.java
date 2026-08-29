package com.carlosarroyoam.authorizationserver.auth;

import com.carlosarroyoam.authorizationserver.auth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad {@link User} (tabla {@code users}, clave primaria {@link Long}).
 */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Busca un usuario por su correo electrónico y carga sus roles en la misma consulta mediante un
   * {@link EntityGraph}.
   *
   * @param email correo electrónico del usuario a buscar
   * @return un {@link Optional} con el usuario y sus roles, o vacío si no existe
   */
  @EntityGraph(attributePaths = "roles")
  Optional<User> findByEmail(String email);
}
