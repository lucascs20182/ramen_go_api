package br.com.redventures.ramen_go.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Protein")
@Data
public class ProteinEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageInactive;

  private String imageActive;

  private String name;

  private String description;

  private Double price;

  @OneToMany(mappedBy = "protein")
  private List<OrderEntity> orders;

}
