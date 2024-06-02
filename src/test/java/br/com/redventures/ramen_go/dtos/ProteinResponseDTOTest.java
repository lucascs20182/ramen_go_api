package br.com.redventures.ramen_go.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProteinResponseDTOTest {

  private ProteinResponseDTO proteinResponseDTO;

  @BeforeEach
  void setUp() {
    proteinResponseDTO = new ProteinResponseDTO();
  }

  @Test
  public void testGettersAndSetters() {
    Long id = 1L;
    String imageInactive = "https://example.com/image_inactive.jpg";
    String imageActive = "https://example.com/image_active.jpg";
    String name = "Test Protein";
    String description = "Test Description";
    Double price = 10.0;

    proteinResponseDTO.setId(id);
    proteinResponseDTO.setImageInactive(imageInactive);
    proteinResponseDTO.setImageActive(imageActive);
    proteinResponseDTO.setName(name);
    proteinResponseDTO.setDescription(description);
    proteinResponseDTO.setPrice(price);

    assertNotNull(proteinResponseDTO);
    assertEquals(id, proteinResponseDTO.getId());
    assertEquals(imageInactive, proteinResponseDTO.getImageInactive());
    assertEquals(imageActive, proteinResponseDTO.getImageActive());
    assertEquals(name, proteinResponseDTO.getName());
    assertEquals(description, proteinResponseDTO.getDescription());
    assertEquals(price, proteinResponseDTO.getPrice());
  }

  @Test
  public void testEqualsAndHashCode() {
    ProteinResponseDTO protein1 = new ProteinResponseDTO();
    protein1.setId(1L);
    protein1.setImageInactive("https://example.com/image_inactive.jpg");
    protein1.setImageActive("https://example.com/image_active.jpg");
    protein1.setName("Test Protein");
    protein1.setDescription("Test Description");
    protein1.setPrice(10.0);

    ProteinResponseDTO protein2 = new ProteinResponseDTO();
    protein2.setId(1L);
    protein2.setImageInactive("https://example.com/image_inactive.jpg");
    protein2.setImageActive("https://example.com/image_active.jpg");
    protein2.setName("Test Protein");
    protein2.setDescription("Test Description");
    protein2.setPrice(10.0);

    ProteinResponseDTO protein3 = new ProteinResponseDTO();
    protein3.setId(2L);
    protein3.setImageInactive("https://example.com/image_inactive2.jpg");
    protein3.setImageActive("https://example.com/image_active2.jpg");
    protein3.setName("Test Protein 2");
    protein3.setDescription("Test Description 2");
    protein3.setPrice(12.0);

    // Same object
    assertEquals(protein1, protein1);
    assertEquals(protein1.hashCode(), protein1.hashCode());

    // Different object but same ID and fields
    assertEquals(protein1, protein2);
    assertEquals(protein1.hashCode(), protein2.hashCode());

    // Different ID and fields
    assertNotEquals(protein1, protein3);
    assertNotEquals(protein1.hashCode(), protein3.hashCode());

    // Null
    assertNotEquals(protein1, null);

    // Different class
    assertNotEquals(protein1, new Object());
  }

}
