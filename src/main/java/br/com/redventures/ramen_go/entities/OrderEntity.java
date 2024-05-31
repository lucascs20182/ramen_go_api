package br.com.redventures.ramen_go.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RamenOrder") // Avoid conflicts with the SQL reserved word "order"
@Data
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "broth_id")
  private BrothEntity broth;

  @ManyToOne
  @JoinColumn(name = "protein_id")
  private ProteinEntity protein;

}
