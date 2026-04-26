package com.carlosarroyoam.authorizationserver.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", length = 64, nullable = false)
  private String firstName;

  @Column(name = "last_name", length = 64, nullable = false)
  private String lastName;

  @Column(name = "email", length = 128, nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash", length = 128, nullable = false)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 32, nullable = false)
  private UserStatus status;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
