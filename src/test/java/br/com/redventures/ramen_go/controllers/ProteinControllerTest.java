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
import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.ProteinService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProteinControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProteinService proteinService;

  @MockBean
  private ApiKeyService apiKeyService;

  @InjectMocks
  private ProteinController proteinController;

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

    ProteinEntity protein1 = new ProteinEntity();
    protein1.setId(1L);
    protein1.setName("Chicken");

    ProteinEntity protein2 = new ProteinEntity();
    protein2.setId(2L);
    protein2.setName("Pork");

    List<ProteinEntity> mockProteins = Arrays.asList(protein1, protein2);

    when(proteinService.listAll()).thenReturn(mockProteins);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/proteins")
        .header("x-api-key", mockApiKeyEntity.getApiKey())
    );

    // Assert
    resultActions.andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name").value("Chicken"))
      .andExpect(jsonPath("$[1].name").value("Pork"));
  }

  @Test
  public void testListAllWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(get("/proteins"));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  public void testListAllWithInvalidApiKey() throws Exception {

    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(
      get("/proteins")
        .header("x-api-key", "invalid-api-key"));

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

    ProteinEntity protein = new ProteinEntity();
    protein.setName("Beef");

    when(proteinService.create(protein)).thenReturn(protein);

    // Act
    ResultActions resultActions = mockMvc.perform(
      post("/proteins")
        .content("{ \"name\": \"Beef\" }")
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-api-key", mockApiKeyEntity.getApiKey()));

    // Assert
    resultActions.andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value("Beef"));
  }

  @Test
  public void testCreateWithMissingApiKey() throws Exception {

    // Act
    ResultActions resultActions = mockMvc.perform(post("/proteins")
      .content("{ \"name\": \"Beef\" }")
      .contentType(MediaType.APPLICATION_JSON));

    // Assert
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  public void testCreateWithInvalidApiKey() throws Exception {
    // Arrange
    when(apiKeyService.validate(anyString())).thenReturn(false);

    // Act
    ResultActions resultActions = mockMvc.perform(post("/proteins")
      .content("{ \"name\": \"Beef\" }")
      .contentType(MediaType.APPLICATION_JSON)
      .header("x-api-key", "invalid-api-key"));

    // Assert
    resultActions.andExpect(status().isUnauthorized());
  }

}
