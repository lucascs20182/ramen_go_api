package br.com.redventures.ramen_go.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.BrothService;

@SpringBootTest
@AutoConfigureMockMvc
public class BrothControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BrothService brothService;

  @MockBean
  private ApiKeyService apiKeyService;

  @InjectMocks
  private BrothController brothController;

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

    BrothEntity broth1 = new BrothEntity();
    broth1.setId(1L);
    broth1.setName("Shoyu");

    BrothEntity broth2 = new BrothEntity();
    broth2.setId(2L);
    broth2.setName("Miso");

    List<BrothEntity> mockBroths = Arrays.asList(broth1, broth2);

    when(brothService.listAll()).thenReturn(mockBroths);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/broths")
        .header("x-api-key", mockApiKeyEntity.getApiKey())
    );

    // Assert
    resultActions.andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name").value("Shoyu"))
      .andExpect(jsonPath("$[1].name").value("Miso"));
  }

  @Test
  public void testListAllWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(get("/broths"));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  public void testListAllWithInvalidApiKey() throws Exception {

    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/broths")
        .header("x-api-key", "invalid-api-key")
    );

    // Assert
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  public void testCreate() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
      "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
      expirationTime);

    when(apiKeyService.validate(anyString())).thenReturn(true);
    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    BrothEntity broth = new BrothEntity();
    broth.setName("Tonkotsu");

    when(brothService.create(broth)).thenReturn(broth);

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/broths")
        .content("{ \"name\": \"Tonkotsu\" }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value("Tonkotsu"));
  }

  @Test
  public void testCreateWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(post("/broths")
      .content("{ \"name\": \"Tonkotsu\" }")
      .contentType(MediaType.APPLICATION_JSON));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  public void testCreateWithInvalidApiKey() throws Exception {
    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(post("/broths")
      .content("{ \"name\": \"Tonkotsu\" }")
      .contentType(MediaType.APPLICATION_JSON)
      .header("x-api-key", "invalid-api-key"));

    // Assert
    resultActions.andExpect(status().isUnauthorized());
  }

}
