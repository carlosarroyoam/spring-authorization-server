package com.carlosarroyoam.authorizationserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;

@Entity
@Table(name = "authorizations")
@Data
public class Authorization {
  @Id private String id;

  @Column private String registeredClientId;

  @Column private String principalName;

  @Column private String authorizationGrantType;

  @Column(length = 1000)
  private String authorizedScopes;

  @Column(length = 4000)
  private String attributes;

  @Column(length = 500)
  private String state;

  @Column(length = 4000)
  private String authorizationCodeValue;

  @Column private Instant authorizationCodeIssuedAt;

  @Column private Instant authorizationCodeExpiresAt;

  @Column private String authorizationCodeMetadata;

  @Column(length = 4000)
  private String accessTokenValue;

  @Column private Instant accessTokenIssuedAt;

  @Column private Instant accessTokenExpiresAt;

  @Column(length = 2000)
  private String accessTokenMetadata;

  @Column private String accessTokenType;

  @Column(length = 1000)
  private String accessTokenScopes;

  @Column(length = 4000)
  private String refreshTokenValue;

  @Column private Instant refreshTokenIssuedAt;

  @Column private Instant refreshTokenExpiresAt;

  @Column(length = 2000)
  private String refreshTokenMetadata;

  @Column(length = 4000)
  private String oidcIdTokenValue;

  @Column private Instant oidcIdTokenIssuedAt;

  @Column private Instant oidcIdTokenExpiresAt;

  @Column(length = 2000)
  private String oidcIdTokenMetadata;

  @Column(length = 2000)
  private String oidcIdTokenClaims;

  @Column(length = 4000)
  private String userCodeValue;

  @Column private Instant userCodeIssuedAt;

  @Column private Instant userCodeExpiresAt;

  @Column(length = 2000)
  private String userCodeMetadata;

  @Column(length = 4000)
  private String deviceCodeValue;

  @Column private Instant deviceCodeIssuedAt;

  @Column private Instant deviceCodeExpiresAt;

  @Column(length = 2000)
  private String deviceCodeMetadata;
}
