package br.com.redventures.ramen_go.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderResponseDTOTest {

  private OrderResponseDTO orderResponseDTO;

  @BeforeEach
  void setUp() {
    orderResponseDTO = new OrderResponseDTO();
  }

  @Test
  public void testGettersAndSetters() {
    String id = "1";
    String description = "Test Description";
    String image = "https://example.com/image.jpg";

    orderResponseDTO.setId(id);
    orderResponseDTO.setDescription(description);
    orderResponseDTO.setImage(image);

    assertNotNull(orderResponseDTO);
    assertEquals(id, orderResponseDTO.getId());
    assertEquals(description, orderResponseDTO.getDescription());
    assertEquals(image, orderResponseDTO.getImage());
  }

  @Test
  public void testEqualsAndHashCode() {
    OrderResponseDTO order1 = new OrderResponseDTO();
    order1.setId("1");
    order1.setDescription("Description 1");
    order1.setImage("https://example.com/image1.jpg");

    OrderResponseDTO order2 = new OrderResponseDTO();
    order2.setId("1");
    order2.setDescription("Description 1");
    order2.setImage("https://example.com/image1.jpg");

    OrderResponseDTO order3 = new OrderResponseDTO();
    order3.setId("2");
    order3.setDescription("Description 2");
    order3.setImage("https://example.com/image2.jpg");

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
