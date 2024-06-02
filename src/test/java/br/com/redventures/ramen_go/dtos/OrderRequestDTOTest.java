package br.com.redventures.ramen_go.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderRequestDTOTest {

  private OrderRequestDTO orderRequestDTO;

  @BeforeEach
  void setUp() {
      orderRequestDTO = new OrderRequestDTO();
  }

  @Test
  public void testGettersAndSetters() {
    Long brothId = 1L;
    Long proteinId = 2L;

    orderRequestDTO.setBrothId(brothId);
    orderRequestDTO.setProteinId(proteinId);

    assertNotNull(orderRequestDTO);
    assertEquals(brothId, orderRequestDTO.getBrothId());
    assertEquals(proteinId, orderRequestDTO.getProteinId());
  }

  @Test
  public void testEqualsAndHashCode() {
    OrderRequestDTO order1 = new OrderRequestDTO();
    order1.setBrothId(1L);
    order1.setProteinId(2L);

    OrderRequestDTO order2 = new OrderRequestDTO();
    order2.setBrothId(1L);
    order2.setProteinId(2L);

    OrderRequestDTO order3 = new OrderRequestDTO();
    order3.setBrothId(3L);
    order3.setProteinId(4L);

    // Same object
    assertEquals(order1, order1);
    assertEquals(order1.hashCode(), order1.hashCode());

    // Different object but same ID and fields
    assertEquals(order1, order2);
    assertEquals(order1.hashCode(), order2.hashCode());

    // Different ID and fields
    assertNotEquals(order1, order3);
    assertNotEquals(order1.hashCode(), order3.hashCode());

    // Null
    assertNotEquals(order1, null);

    // Different class
    assertNotEquals(order1, new Object());
  }

}
