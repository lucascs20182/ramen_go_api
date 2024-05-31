package br.com.redventures.ramen_go.services;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.repositories.ApiKeyRepository;

@Service
public class ApiKeyService {

  @Autowired
  private ApiKeyRepository repository;

  private static final long EXPIRATION_TIME_MS = 3600000; // Expires after 1 hour

  public ApiKeyEntity generate() {

    byte[] randomBytes = new byte[32];

    new SecureRandom().nextBytes(randomBytes);

    String apiKeyValue = Base64.getEncoder().encodeToString(randomBytes);

    Instant now = Instant.now();
    Instant expirationTime = now.plusMillis(EXPIRATION_TIME_MS);

    ApiKeyEntity entity = new ApiKeyEntity(apiKeyValue, expirationTime);

    entity = repository.save(entity);

    return entity;
  }

  public Boolean validate(String apiKey) {

    // Workaround for submitting the technical challenge
    // with a non-expiring API key"
    if (apiKey.equals("x2aSNAg6RfOQyPp+cC8IsopHIa3v3/OUhsiUn/jPebg=")) {
      return true;
    }

    if (repository.existsByApiKey(apiKey)) {
      return true;
    }

    return false;
  }

}
