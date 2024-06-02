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


public class BrothEntityTest {

  private BrothEntity brothEntity;

  @BeforeEach
  void setUp() {
      brothEntity = new BrothEntity();
  }

  @Test
  public void testGettersAndSetters() {

    String activeImage = "https://tech.redventures.com.br/icons/salt/active.svg";
    String inactiveImage = "https://tech.redventures.com.br/icons/salt/inactive.svg";

    brothEntity.setId(1L);
    brothEntity.setName("Test Broth");
    brothEntity.setDescription("Test Description");
    brothEntity.setPrice(10.0);
    brothEntity.setImageActive(activeImage);
    brothEntity.setImageInactive(inactiveImage);

    List<OrderEntity> orders = new ArrayList<>();
    brothEntity.setOrders(orders);

    assertNotNull(brothEntity);
    assertEquals(1L, brothEntity.getId());
    assertEquals("Test Broth", brothEntity.getName());
    assertEquals("Test Description", brothEntity.getDescription());
    assertEquals(10.0, brothEntity.getPrice());
    assertEquals(activeImage, brothEntity.getImageActive());
    assertEquals(inactiveImage, brothEntity.getImageInactive());
    assertEquals(orders, brothEntity.getOrders());
  }

  @Test
  public void testEqualsAndHashCode() {
    BrothEntity broth1 = new BrothEntity();
    broth1.setId(1L);
    BrothEntity broth2 = new BrothEntity();
    broth2.setId(1L);
    BrothEntity broth3 = new BrothEntity();
    broth3.setId(2L);
    BrothEntity broth4 = new BrothEntity();
    broth4.setName("Shoyu");

    // Same object
    assertEquals(broth1, broth1);
    assertEquals(broth1.hashCode(), broth1.hashCode());

    // Different object but same ID
    assertEquals(broth1, broth2);
    assertEquals(broth1.hashCode(), broth2.hashCode());

    // Different ID
    assertNotEquals(broth1, broth3);
    assertNotEquals(broth1.hashCode(), broth3.hashCode());

    // Different name
    assertNotEquals(broth1, broth4);
    assertNotEquals(broth1.hashCode(), broth4.hashCode());

    // Null
    assertNotEquals(broth1, null);

    // Different class
    assertNotEquals(broth1, new Object());

    // canEqual
    assertTrue(broth1.equals(broth2));
    assertFalse(broth1.equals(new Object()));
  }

}
