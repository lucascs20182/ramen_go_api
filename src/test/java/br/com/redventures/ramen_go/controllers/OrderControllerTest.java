package br.com.redventures.ramen_go.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.redventures.ramen_go.dtos.OrderRequestDTO;
import br.com.redventures.ramen_go.dtos.OrderResponseDTO;
import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.entities.OrderEntity;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @MockBean
  private ApiKeyService apiKeyService;

  @InjectMocks
  private OrderController orderController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testListAll() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    OrderEntity order1 = new OrderEntity();
    order1.setId(1L);
    order1.setOrderId("order1");
    order1.setDescription("Ramen with Shoyu and Pork");
    order1.setImage("image1");

    OrderEntity order2 = new OrderEntity();
    order1.setId(2L);
    order2.setOrderId("order2");
    order2.setDescription("Ramen with Miso and Chicken");
    order2.setImage("image2");

    List<OrderEntity> mockOrders = Arrays.asList(order1, order2);

    when(orderService.listAll()).thenReturn(mockOrders);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/order")
        .header("x-api-key", mockApiKeyEntity.getApiKey())
    );

    // Assert
    resultActions.andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("order1"))
      .andExpect(jsonPath("$[0].description").value("Ramen with Shoyu and Pork"))
      .andExpect(jsonPath("$[0].image").value("image1"))
      .andExpect(jsonPath("$[1].id").value("order2"))
      .andExpect(jsonPath("$[1].description").value("Ramen with Miso and Chicken"))
      .andExpect(jsonPath("$[1].image").value("image2"));
  }

  @Test
  public void testListAllWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(get("/order"));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }


  @Test
  public void testListAllWithInvalidApiKey() throws Exception {

    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/order")
        .header("x-api-key", "invalid-api-key"));

    // Assert
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  public void testListAllWithInternalServerError() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    when(orderService.listAll())
      .thenThrow(new RuntimeException("Something went wrong"));

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/order")
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isInternalServerError());
  }

  @Test
  public void testPlaceOrder() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    OrderRequestDTO requestDTO = new OrderRequestDTO();
    requestDTO.setBrothId(1L);
    requestDTO.setProteinId(1L);

    OrderResponseDTO mockResponseDTO = new OrderResponseDTO();
    mockResponseDTO.setId("order123");
    mockResponseDTO.setDescription("Ramen with Shoyu and Pork");
    mockResponseDTO.setImage("image123");

    when(orderService.placeOrder(requestDTO)).thenReturn(mockResponseDTO);

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/order")
        .content("{ \"brothId\": 1, \"proteinId\": 1 }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value("order123"))
      .andExpect(jsonPath("$.description").value("Ramen with Shoyu and Pork"))
      .andExpect(jsonPath("$.image").value("image123"));
  }

  @Test
  public void testPlaceOrderWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/order")
        .content("{ \"brothId\": 1, \"proteinId\": 1 }")
        .contentType(MediaType.APPLICATION_JSON));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  public void testPlaceOrderWithInvalidApiKey() throws Exception {

    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/order")
        .content("{ \"brothId\": 1, \"proteinId\": 1 }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", "invalid-api-key"));

    // Assert
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  public void testPlaceOrderWithMissingBrothId() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
        "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
        expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    OrderRequestDTO requestDTO = new OrderRequestDTO();
    requestDTO.setBrothId(null);
    requestDTO.setProteinId(1L);

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/order")
        .content("{ \"brothId\": null, \"proteinId\": 1 }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void testPlaceOrderWithInternalServerError() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    OrderRequestDTO requestDTO = new OrderRequestDTO();
    requestDTO.setBrothId(1L);
    requestDTO.setProteinId(1L);

    when(orderService.placeOrder(requestDTO))
      .thenThrow(new RuntimeException("Something went wrong"));

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/order")
        .content("{ \"brothId\": 1, \"proteinId\": 1 }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isInternalServerError());
  }

}
