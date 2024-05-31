package br.com.redventures.ramen_go.tasks;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.repositories.ApiKeyRepository;

@Component
public class ApiKeyCleanupTask {

  private final ApiKeyRepository repository;

  public ApiKeyCleanupTask(ApiKeyRepository repository) {
    this.repository = repository;
  }

  @Scheduled(fixedRate = 3600000) // Run every hour
  public void cleanupExpiredApiKeys() {
    Instant now = Instant.now();
    List<ApiKeyEntity> expiredApiKeys = repository.findByExpirationTimeBefore(now);

    repository.deleteAll(expiredApiKeys);
  }

}
