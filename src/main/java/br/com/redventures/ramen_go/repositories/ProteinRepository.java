package br.com.redventures.ramen_go.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.redventures.ramen_go.entities.ProteinEntity;

@Repository
public interface ProteinRepository extends JpaRepository<ProteinEntity, Long> { }
