package br.com.redventures.ramen_go.dtos;

import lombok.Data;

@Data
public class BrothResponseDTO {

  private Long id;

  private String imageInactive;

  private String imageActive;

  private String name;

  private String description;

  private Double price;

}
