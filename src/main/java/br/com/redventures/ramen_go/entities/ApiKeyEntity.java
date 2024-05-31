package br.com.redventures.ramen_go.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ApiKey")
@Data
public class ApiKeyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String apiKey;

  private Instant expirationTime;

  public ApiKeyEntity(String apiKey, Instant expirationTime) {
    this.apiKey = apiKey;
    this.expirationTime = expirationTime;
  }

}
