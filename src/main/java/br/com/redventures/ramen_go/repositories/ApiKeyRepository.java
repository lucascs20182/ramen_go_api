package br.com.redventures.ramen_go.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.redventures.ramen_go.entities.ApiKeyEntity;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {

  List<ApiKeyEntity> findByExpirationTimeBefore(Instant expirationTime);

  boolean existsByApiKey(String apiKey);

}
