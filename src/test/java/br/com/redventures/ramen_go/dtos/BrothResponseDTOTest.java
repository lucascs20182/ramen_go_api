package br.com.redventures.ramen_go.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrothResponseDTOTest {

  private BrothResponseDTO brothResponseDTO;

  @BeforeEach
  void setUp() {
      brothResponseDTO = new BrothResponseDTO();
  }

  @Test
  public void testGettersAndSetters() {
    Long id = 1L;
    String imageInactive = "https://example.com/inactive.png";
    String imageActive = "https://example.com/active.png";
    String name = "Test Broth";
    String description = "Test Description";
    Double price = 10.0;

    brothResponseDTO.setId(id);
    brothResponseDTO.setImageInactive(imageInactive);
    brothResponseDTO.setImageActive(imageActive);
    brothResponseDTO.setName(name);
    brothResponseDTO.setDescription(description);
    brothResponseDTO.setPrice(price);

    assertNotNull(brothResponseDTO);
    assertEquals(id, brothResponseDTO.getId());
    assertEquals(imageInactive, brothResponseDTO.getImageInactive());
    assertEquals(imageActive, brothResponseDTO.getImageActive());
    assertEquals(name, brothResponseDTO.getName());
    assertEquals(description, brothResponseDTO.getDescription());
    assertEquals(price, brothResponseDTO.getPrice());
  }

  @Test
  public void testEqualsAndHashCode() {
    BrothResponseDTO broth1 = new BrothResponseDTO();
    broth1.setId(1L);
    broth1.setName("Shoyu");
    broth1.setDescription("Rich soy sauce flavor");
    broth1.setPrice(12.0);
    broth1.setImageActive("https://example.com/active.png");
    broth1.setImageInactive("https://example.com/inactive.png");

    BrothResponseDTO broth2 = new BrothResponseDTO();
    broth2.setId(1L);
    broth2.setName("Shoyu");
    broth2.setDescription("Rich soy sauce flavor");
    broth2.setPrice(12.0);
    broth2.setImageActive("https://example.com/active.png");
    broth2.setImageInactive("https://example.com/inactive.png");

    BrothResponseDTO broth3 = new BrothResponseDTO();
    broth3.setId(2L);
    broth3.setName("Miso");
    broth3.setDescription("Rich miso flavor");
    broth3.setPrice(13.0);
    broth3.setImageActive("https://example.com/active2.png");
    broth3.setImageInactive("https://example.com/inactive2.png");

    // Same object
    assertEquals(broth1, broth1);
    assertEquals(broth1.hashCode(), broth1.hashCode());

    // Different object but same ID and fields
    assertEquals(broth1, broth2);
    assertEquals(broth1.hashCode(), broth2.hashCode());

    // Different ID and fields
    assertNotEquals(broth1, broth3);
    assertNotEquals(broth1.hashCode(), broth3.hashCode());

    // Null
    assertNotEquals(broth1, null);

    // Different class
    assertNotEquals(broth1, new Object());
  }

}
