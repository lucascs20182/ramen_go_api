package br.com.redventures.ramen_go.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.repositories.BrothRepository;
import br.com.redventures.ramen_go.services.BrothService;

public class BrothServiceTest {

  @Mock
  private BrothRepository repository;

  @InjectMocks
  private BrothService brothService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testListAll() {

    // Arrange
    BrothEntity broth1 = new BrothEntity();
    broth1.setId(1L);
    broth1.setName("Shoyu");

    BrothEntity broth2 = new BrothEntity();
    broth2.setId(2L);
    broth2.setName("Miso");

    List<BrothEntity> mockBroths = Arrays.asList(broth1, broth2);

    when(repository.findAll()).thenReturn(mockBroths);

    // Act
    List<BrothEntity> result = brothService.listAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Shoyu", result.get(0).getName());
    assertEquals("Miso", result.get(1).getName());
    verify(repository, times(1)).findAll();

  }

  @Test
  public void testCreate() {

    // Arrange
    BrothEntity broth = new BrothEntity();
    broth.setName("Tonkotsu");
    when(repository.save(any(BrothEntity.class))).thenReturn(broth);

    // Act
    BrothEntity result = brothService.create(broth);

    // Assert
    assertNotNull(result);
    assertEquals("Tonkotsu", result.getName());

    verify(repository, times(1))
      .save(any(BrothEntity.class));
  }

}
