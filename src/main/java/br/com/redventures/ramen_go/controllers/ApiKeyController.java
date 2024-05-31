package br.com.redventures.ramen_go.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.redventures.ramen_go.dtos.ApiKeyResponseDTO;
import br.com.redventures.ramen_go.entities.ApiKeyEntity;
import br.com.redventures.ramen_go.services.ApiKeyService;

@RestController
@RequestMapping("/apikey")
public class ApiKeyController {

  @Autowired
  ApiKeyService service;

  @PostMapping
  public ResponseEntity<ApiKeyResponseDTO> generate() {
    ApiKeyEntity entity = service.generate();
    ApiKeyResponseDTO dto = new ApiKeyResponseDTO();

    BeanUtils.copyProperties(entity, dto);

    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

}
