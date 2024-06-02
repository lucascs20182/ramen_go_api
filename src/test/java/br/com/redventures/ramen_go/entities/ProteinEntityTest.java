package br.com.redventures.ramen_go.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProteinEntityTest {

  private ProteinEntity proteinEntity;

  @BeforeEach
  void setUp() {
      proteinEntity = new ProteinEntity();
  }

  @Test
  public void testGettersAndSetters() {

    String activeImage = "https://tech.redventures.com.br/icons/salt/active.svg";
    String inactiveImage = "https://tech.redventures.com.br/icons/salt/inactive.svg";

    proteinEntity.setId(1L);
    proteinEntity.setName("Test Protein");
    proteinEntity.setDescription("Test Description");
    proteinEntity.setPrice(10.0);
    proteinEntity.setImageActive(activeImage);
    proteinEntity.setImageInactive(inactiveImage);

    List<OrderEntity> orders = new ArrayList<>();
    proteinEntity.setOrders(orders);

    assertNotNull(proteinEntity);
    assertEquals(1L, proteinEntity.getId());
    assertEquals("Test Protein", proteinEntity.getName());
    assertEquals("Test Description", proteinEntity.getDescription());
    assertEquals(10.0, proteinEntity.getPrice());
    assertEquals(activeImage, proteinEntity.getImageActive());
    assertEquals(inactiveImage, proteinEntity.getImageInactive());
    assertEquals(orders, proteinEntity.getOrders());
  }

  @Test
  public void testEqualsAndHashCode() {
    ProteinEntity protein1 = new ProteinEntity();
    protein1.setId(1L);
    ProteinEntity protein2 = new ProteinEntity();
    protein2.setId(1L);
    ProteinEntity protein3 = new ProteinEntity();
    protein3.setId(2L);
    ProteinEntity protein4 = new ProteinEntity();
    protein4.setName("Shoyu");

    // Same object
    assertEquals(protein1, protein1);
    assertEquals(protein1.hashCode(), protein1.hashCode());

    // Different object but same ID
    assertEquals(protein1, protein2);
    assertEquals(protein1.hashCode(), protein2.hashCode());

    // Different ID
    assertNotEquals(protein1, protein3);
    assertNotEquals(protein1.hashCode(), protein3.hashCode());

    // Different name
    assertNotEquals(protein1, protein4);
    assertNotEquals(protein1.hashCode(), protein4.hashCode());

    // Null
    assertNotEquals(protein1, null);

    // Different class
    assertNotEquals(protein1, new Object());

    // canEqual
    assertTrue(protein1.equals(protein2));
    assertFalse(protein1.equals(new Object()));
  }

}
