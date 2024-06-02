package br.com.redventures.ramen_go.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiKeyResponseDTOTest {

  private ApiKeyResponseDTO apiKeyResponseDTO;

  @BeforeEach
  void setUp() {
    apiKeyResponseDTO = new ApiKeyResponseDTO();
  }

  @Test
  public void testGettersAndSetters() {
    apiKeyResponseDTO.setApiKey("testApiKey");

    assertNotNull(apiKeyResponseDTO);
    assertEquals("testApiKey", apiKeyResponseDTO.getApiKey());
  }

  @Test
  public void testEqualsAndHashCode() {
    ApiKeyResponseDTO dto1 = new ApiKeyResponseDTO();
    dto1.setApiKey("testApiKey");

    ApiKeyResponseDTO dto2 = new ApiKeyResponseDTO();
    dto2.setApiKey("testApiKey");

    ApiKeyResponseDTO dto3 = new ApiKeyResponseDTO();
    dto3.setApiKey("differentApiKey");

    // Same object
    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    // Different object but same apiKey
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    // Different apiKey
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());

    // Null
    assertNotEquals(dto1, null);

    // Different class
    assertNotEquals(dto1, new Object());
  }

}
