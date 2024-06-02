package br.com.redventures.ramen_go.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.repositories.ApiKeyRepository;
import br.com.redventures.ramen_go.services.ApiKeyService;

public class ApiKeyServiceTest {

  @Mock
  private ApiKeyRepository repository;

  @InjectMocks
  private ApiKeyService apiKeyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGenerate() {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime
    );

    when(repository.save(any(ApiKeyEntity.class)))
      .thenReturn(mockEntity);

    // Act
    ApiKeyEntity generatedApiKey = apiKeyService.generate();

    // Assert
    assertNotNull(generatedApiKey);

    assertEquals(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      generatedApiKey.getApiKey()
    );

    assertTrue(expirationTime.isAfter(Instant.now()));
    assertTrue(expirationTime.isAfter(expirationTime.minusMillis(1)));

    verify(repository, times(1))
      .save(any(ApiKeyEntity.class));
  }

  @Test
  void testValidateDefaultKey() {

    // Arrange
    String nonExpiringKey = "x2aSNAg6RfOQyPp+cC8IsopHIa3v3/OUhsiUn/jPebg=";

    // Act & Assert
    assertTrue(apiKeyService.validate(nonExpiringKey));
  }

  @Test
  void testValidateExistingKey() {

    // Arrange
    String key = "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf";

    when(repository.existsByApiKey(key))
        .thenReturn(true);

    // Act & Assert
    assertTrue(apiKeyService.validate(key));
  }

  @Test
  void testValidateNonExistingKey() {

    // Arrange
    String key = "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf";

    when(repository.existsByApiKey(key))
        .thenReturn(false);

    // Act & Assert
    assertFalse(apiKeyService.validate(key));
  }

}
