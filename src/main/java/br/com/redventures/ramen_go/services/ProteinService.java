package br.com.redventures.ramen_go.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.repositories.ProteinRepository;

@Service
public class ProteinService {

  @Autowired
  private ProteinRepository repository;

  public List<ProteinEntity> listAll() {
    return repository.findAll();
  }

  public ProteinEntity create(ProteinEntity entity) {
    return repository.save(entity);
  }

}
