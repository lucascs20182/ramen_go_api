package br.com.redventures.ramen_go.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderEntityTest {

  private OrderEntity orderEntity;

  @BeforeEach
  void setUp() {
      orderEntity = new OrderEntity();
  }

  @Test
  public void testGettersAndSetters() {
    BrothEntity broth = new BrothEntity();
    broth.setId(1L);
    broth.setName("Miso");

    ProteinEntity protein = new ProteinEntity();
    protein.setId(1L);
    protein.setName("Chicken");

    orderEntity.setId(1L);
    orderEntity.setOrderId("order123");
    orderEntity.setDescription("A delicious ramen order");
    orderEntity.setImage("http://example.com/ramen.jpg");
    orderEntity.setBroth(broth);
    orderEntity.setProtein(protein);

    assertNotNull(orderEntity);
    assertEquals(1L, orderEntity.getId());
    assertEquals("order123", orderEntity.getOrderId());
    assertEquals("A delicious ramen order", orderEntity.getDescription());
    assertEquals("http://example.com/ramen.jpg", orderEntity.getImage());
    assertEquals(broth, orderEntity.getBroth());
    assertEquals(protein, orderEntity.getProtein());
  }

  @Test
  public void testEqualsAndHashCode() {
    OrderEntity order1 = new OrderEntity();
    order1.setId(1L);
    order1.setOrderId("order123");

    OrderEntity order2 = new OrderEntity();
    order2.setId(1L);
    order2.setOrderId("order123");

    OrderEntity order3 = new OrderEntity();
    order3.setId(2L);
    order3.setOrderId("order124");

    OrderEntity order4 = new OrderEntity();
    order4.setDescription("Another order");

    // Same object
    assertEquals(order1, order1);
    assertEquals(order1.hashCode(), order1.hashCode());

    // Different object but same ID and orderId
    assertEquals(order1, order2);
    assertEquals(order1.hashCode(), order2.hashCode());

    // Different ID
    assertNotEquals(order1, order3);
    assertNotEquals(order1.hashCode(), order3.hashCode());

    // Different orderId
    order3.setOrderId("order123");
    assertNotEquals(order1, order3);
    assertNotEquals(order1.hashCode(), order3.hashCode());

    // Different description
    assertNotEquals(order1, order4);
    assertNotEquals(order1.hashCode(), order4.hashCode());

    // Null
    assertNotEquals(order1, null);

    // Different class
    assertNotEquals(order1, new Object());

    // canEqual
    assertTrue(order1.equals(order2));
    assertFalse(order1.equals(new Object()));
  }

}
