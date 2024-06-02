package br.com.redventures.ramen_go.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.redventures.ramen_go.dtos.OrderRequestDTO;
import br.com.redventures.ramen_go.dtos.OrderResponseDTO;
import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.entities.OrderEntity;
import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.exceptions.InvalidRequestException;
import br.com.redventures.ramen_go.repositories.BrothRepository;
import br.com.redventures.ramen_go.repositories.OrderRepository;
import br.com.redventures.ramen_go.repositories.ProteinRepository;
import br.com.redventures.ramen_go.services.OrderService;

public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private BrothRepository brothRepository;

  @Mock
  private ProteinRepository proteinRepository;

  @InjectMocks
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testListAll() {

    // Arrange
    OrderEntity order1 = new OrderEntity();
    order1.setOrderId("123456");

    OrderEntity order2 = new OrderEntity();
    order2.setOrderId("789012");

    List<OrderEntity> mockOrders = Arrays.asList(order1, order2);

    when(orderRepository.findAll()).thenReturn(mockOrders);

    // Act
    List<OrderEntity> result = orderService.listAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("123456", result.get(0).getOrderId());
    assertEquals("789012", result.get(1).getOrderId());
    verify(orderRepository, times(1)).findAll();
  }

  @Test
  public void testPlaceOrder() {

    // Arrange
    OrderRequestDTO orderRequest = new OrderRequestDTO();
    orderRequest.setBrothId(1L);
    orderRequest.setProteinId(1L);

    BrothEntity brothEntity = new BrothEntity();
    brothEntity.setId(1L);
    brothEntity.setName("Shoyu");

    ProteinEntity proteinEntity = new ProteinEntity();
    proteinEntity.setId(1L);
    proteinEntity.setName("Chashu");

    when(brothRepository.findById(1L))
      .thenReturn(Optional.of(brothEntity));

    when(proteinRepository.findById(1L))
      .thenReturn(Optional.of(proteinEntity));

    when(orderRepository.save(any(OrderEntity.class)))
      .thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    OrderResponseDTO orderResponse = orderService.placeOrder(orderRequest);

    // Assert
    assertNotNull(orderResponse);
    assertEquals("Shoyu and Chashu", orderResponse.getDescription());

    assertEquals(
      "https://tech.redventures.com.br/icons/ramen/ramenChasu.png",
      orderResponse.getImage()
    );

    verify(brothRepository, times(1)).findById(1L);
    verify(proteinRepository, times(1)).findById(1L);

    verify(orderRepository, times(1))
      .save(any(OrderEntity.class));
  }

  @Test
  public void testPlaceOrderWithInvalidBrothId() {

    // Arrange
    OrderRequestDTO orderRequest = new OrderRequestDTO();
    orderRequest.setBrothId(1L);
    orderRequest.setProteinId(1L);

    when(brothRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    InvalidRequestException exception = assertThrows(
      InvalidRequestException.class, () -> {
        orderService.placeOrder(orderRequest);
      }
    );

    assertEquals("Invalid broth ID", exception.getMessage());
    verify(brothRepository, times(1)).findById(1L);
  }

  @Test
  public void testPlaceOrderWithInvalidProteinId() {

    // Arrange
    OrderRequestDTO orderRequest = new OrderRequestDTO();
    orderRequest.setBrothId(1L);
    orderRequest.setProteinId(1L);

    BrothEntity brothEntity = new BrothEntity();
    brothEntity.setId(1L);

    when(brothRepository.findById(1L)).thenReturn(Optional.of(brothEntity));
    when(proteinRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    InvalidRequestException exception = assertThrows(
      InvalidRequestException.class, () -> {
        orderService.placeOrder(orderRequest);
      }
    );

    assertEquals("Invalid protein ID", exception.getMessage());
    verify(brothRepository, times(1)).findById(1L);
    verify(proteinRepository, times(1)).findById(1L);
  }

  @Test
  public void testGenerateUniqueOrderId() {

    // Arrange
    OrderRequestDTO orderRequest = new OrderRequestDTO();
    orderRequest.setBrothId(1L);
    orderRequest.setProteinId(1L);

    when(orderRepository.existsByOrderId(any(String.class)))
      .thenReturn(false);

    BrothEntity mockBroth = new BrothEntity();
    mockBroth.setId(1L);

    when(brothRepository.findById(any(Long.class)))
      .thenReturn(Optional.of(mockBroth));

    ProteinEntity mockProtein = new ProteinEntity();
    mockBroth.setId(1L);

    when(proteinRepository.findById(any(Long.class)))
      .thenReturn(Optional.of(mockProtein));

    when(orderRepository.save(any(OrderEntity.class)))
      .thenAnswer(invocation -> {
        OrderEntity orderEntity = invocation.getArgument(0);
        orderEntity.setOrderId("123456");
        return orderEntity;
      });

    // Act
    String orderId = orderService.placeOrder(orderRequest).getId();

    // Assert
    assertNotNull(orderId);
    assertEquals("123456", orderId);
  }

}
