package br.com.redventures.ramen_go.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.repositories.BrothRepository;

@Service
public class BrothService {

  @Autowired
  private BrothRepository repository;

  public List<BrothEntity> listAll() {
    return repository.findAll();
  }

  public BrothEntity create(BrothEntity entity) {
    return repository.save(entity);
  }

}
