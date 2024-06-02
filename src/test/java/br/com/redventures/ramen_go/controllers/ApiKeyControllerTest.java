package br.com.redventures.ramen_go.controllers;

import static org.mockito.Mockito.when;

import java.time.Instant;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.services.ApiKeyService;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiKeyControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApiKeyService apiKeyService;

  @InjectMocks
  private ApiKeyController apiKeyController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testGenerateApiKey() throws Exception {

    // Arrange
    Instant expirationTime = Instant.now().plusMillis(3600000);

    ApiKeyEntity mockApiKeyEntity = new ApiKeyEntity(
        "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf",
        expirationTime);

    when(apiKeyService.generate()).thenReturn(mockApiKeyEntity);

    // Act
    ResultActions resultActions = mockMvc.perform(post("/apikey"));

    // Assert
    resultActions.andExpect(status().isOk())
      .andExpect(
        MockMvcResultMatchers.content()
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(
        jsonPath("$.apiKey")
          .value(mockApiKeyEntity.getApiKey())
      );
  }

}
