package br.com.redventures.ramen_go.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.redventures.ramen_go.dtos.ProteinResponseDTO;
import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.exceptions.InvalidApiKeyException;
import br.com.redventures.ramen_go.exceptions.MissingApiKeyException;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.ProteinService;

@RestController
@RequestMapping("/proteins")
public class ProteinController {

  @Autowired
  private ProteinService service;

  @Autowired
  private ApiKeyService apiKeyService;

  @GetMapping
  public ResponseEntity<?> listAll(
    @RequestHeader(name = "x-api-key", required = false) String apiKey
  ) {

    if (apiKey == null) {
      throw new MissingApiKeyException();
    }

    if (!apiKeyService.validate(apiKey)) {
      throw new InvalidApiKeyException();
    }

    List<ProteinEntity> entities = service.listAll();

    List<ProteinResponseDTO> responseDTOs = new ArrayList<ProteinResponseDTO>();

    entities.forEach(entity -> {
      ProteinResponseDTO responseDTO = new ProteinResponseDTO();
      BeanUtils.copyProperties(entity, responseDTO);

      responseDTOs.add(responseDTO);
    });

    return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
  }

  @PostMapping
  public ResponseEntity<?> create(
    @RequestHeader(name = "x-api-key", required = false) String apiKey,
    @RequestBody ProteinEntity protein
  ) {

    if (apiKey == null) {
      throw new MissingApiKeyException();
    }

    if (!apiKeyService.validate(apiKey)) {
      throw new InvalidApiKeyException();
    }

    ProteinEntity entity = service.create(protein);
    ProteinResponseDTO responseDTO = new ProteinResponseDTO();

    BeanUtils.copyProperties(entity, responseDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

}
