package com.carlosarroyoam.authorizationserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Entity
@Table(name = "authorization_consents")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Data
public class AuthorizationConsent {
  @Id private String registeredClientId;

  @Id private String principalName;

  @Column(length = 1000)
  private String authorities;

  @Data
  public static class AuthorizationConsentId implements Serializable {
    private static final long serialVersionUID = 7939944128277884752L;
    private String registeredClientId;
    private String principalName;

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      AuthorizationConsentId that = (AuthorizationConsentId) o;
      return registeredClientId.equals(that.registeredClientId)
          && principalName.equals(that.principalName);
    }

    @Override
    public int hashCode() {
      return Objects.hash(registeredClientId, principalName);
    }
  }
}
