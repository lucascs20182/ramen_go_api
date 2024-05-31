package br.com.redventures.ramen_go.controllers;

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

import br.com.redventures.ramen_go.dtos.BrothResponseDTO;
import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.exceptions.InvalidApiKeyException;
import br.com.redventures.ramen_go.exceptions.MissingApiKeyException;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.BrothService;

@RestController
@RequestMapping("/broths")
public class BrothController {

  @Autowired
  private BrothService service;

  @Autowired
  private ApiKeyService apiKeyService;

  @GetMapping
  public ResponseEntity<?> listAll(@RequestHeader(name = "x-api-key", required = false) String apiKey) {

    if (apiKey == null) {
      throw new MissingApiKeyException();
    }

    if (!apiKeyService.validate(apiKey)) {
      throw new InvalidApiKeyException();
    }

    List<BrothEntity> entities = service.listAll();
    return ResponseEntity.status(HttpStatus.OK).body(entities);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestHeader(name = "x-api-key", required = false) String apiKey, @RequestBody BrothEntity broth) {

    if (apiKey == null) {
      throw new MissingApiKeyException();
    }

    if (!apiKeyService.validate(apiKey)) {
      throw new InvalidApiKeyException();
    }

    BrothEntity entity = service.create(broth);
    BrothResponseDTO responseDTO = new BrothResponseDTO();

    BeanUtils.copyProperties(entity, responseDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

}
