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

import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.repositories.ProteinRepository;
import br.com.redventures.ramen_go.services.ProteinService;

public class ProteinServiceTest {

  @Mock
  private ProteinRepository repository;

  @InjectMocks
  private ProteinService proteinService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testListAll() {

    // Arrange
    ProteinEntity protein1 = new ProteinEntity();
    protein1.setId(1L);
    protein1.setName("Chicken");

    ProteinEntity protein2 = new ProteinEntity();
    protein2.setId(2L);
    protein2.setName("Pork");

    List<ProteinEntity> mockProteins = Arrays.asList(protein1, protein2);

    when(repository.findAll()).thenReturn(mockProteins);

    // Act
    List<ProteinEntity> result = proteinService.listAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Chicken", result.get(0).getName());
    assertEquals("Pork", result.get(1).getName());
    verify(repository, times(1)).findAll();
  }

  @Test
  public void testCreate() {

    // Arrange
    ProteinEntity protein = new ProteinEntity();
    protein.setName("Beef");
    when(repository.save(any(ProteinEntity.class))).thenReturn(protein);

    // Act
    ProteinEntity result = proteinService.create(protein);

    // Assert
    assertNotNull(result);
    assertEquals("Beef", result.getName());

    verify(repository, times(1))
      .save(any(ProteinEntity.class));
  }

}
