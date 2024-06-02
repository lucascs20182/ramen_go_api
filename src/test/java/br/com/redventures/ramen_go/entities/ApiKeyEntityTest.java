package br.com.redventures.ramen_go.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiKeyEntityTest {

  private ApiKeyEntity apiKeyEntity;

  @BeforeEach
  void setUp() {
    apiKeyEntity = new ApiKeyEntity("testApiKey", Instant.now());
  }

  @Test
  public void testGettersAndSetters() {
    Instant now = Instant.now();
    apiKeyEntity.setId(1L);
    apiKeyEntity.setApiKey("newApiKey");
    apiKeyEntity.setExpirationTime(now);

    assertNotNull(apiKeyEntity);
    assertEquals(1L, apiKeyEntity.getId());
    assertEquals("newApiKey", apiKeyEntity.getApiKey());
    assertEquals(now, apiKeyEntity.getExpirationTime());
  }

  @Test
  public void testEqualsAndHashCode() {
    Instant now = Instant.now();

    ApiKeyEntity apiKey1 = new ApiKeyEntity("apiKey1", now);
    apiKey1.setId(1L);

    ApiKeyEntity apiKey2 = new ApiKeyEntity("apiKey1", now);
    apiKey2.setId(1L);

    ApiKeyEntity apiKey3 = new ApiKeyEntity("apiKey2", now);
    apiKey3.setId(2L);

    ApiKeyEntity apiKey4 = new ApiKeyEntity(
      "apiKey1",
      now.plusSeconds(3600)
    );

    // Same object
    assertEquals(apiKey1, apiKey1);
    assertEquals(apiKey1.hashCode(), apiKey1.hashCode());

    // Different object but same ID and apiKey
    assertEquals(apiKey1, apiKey2);
    assertEquals(apiKey1.hashCode(), apiKey2.hashCode());

    // Different ID
    assertNotEquals(apiKey1, apiKey3);
    assertNotEquals(apiKey1.hashCode(), apiKey3.hashCode());

    // Different apiKey
    assertNotEquals(apiKey1, apiKey3);
    assertNotEquals(apiKey1.hashCode(), apiKey3.hashCode());

    // Different expirationTime
    assertNotEquals(apiKey1, apiKey4);
    assertNotEquals(apiKey1.hashCode(), apiKey4.hashCode());

    // Null
    assertNotEquals(apiKey1, null);

    // Different class
    assertNotEquals(apiKey1, new Object());

    // canEqual
    assertTrue(apiKey1.equals(apiKey2));
    assertFalse(apiKey1.equals(new Object()));
  }

}
