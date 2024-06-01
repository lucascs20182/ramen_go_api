package br.com.redventures.ramen_go.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.redventures.ramen_go.dtos.OrderRequestDTO;
import br.com.redventures.ramen_go.dtos.OrderResponseDTO;
import br.com.redventures.ramen_go.entities.OrderEntity;
import br.com.redventures.ramen_go.exceptions.InternalServerErrorException;
import br.com.redventures.ramen_go.exceptions.InvalidApiKeyException;
import br.com.redventures.ramen_go.exceptions.InvalidRequestException;
import br.com.redventures.ramen_go.exceptions.MissingApiKeyException;
import br.com.redventures.ramen_go.services.ApiKeyService;
import br.com.redventures.ramen_go.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  @Autowired
  private OrderService service;

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

    List<OrderEntity> entities = service.listAll();

    List<OrderResponseDTO> responseDTOs = new ArrayList<OrderResponseDTO>();

    entities.forEach(entity -> {
      OrderResponseDTO responseDTO = new OrderResponseDTO();
      BeanUtils.copyProperties(entity, responseDTO);

      responseDTOs.add(responseDTO);
    });

    return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
  }

  @PostMapping
  public ResponseEntity<?> placeOrder(
    @RequestHeader(name = "x-api-key", required = false) String apiKey,
    @RequestBody OrderRequestDTO orderRequest
  ) {

    if (apiKey == null) {
      throw new MissingApiKeyException();
    }

    if (!apiKeyService.validate(apiKey)) {
      throw new InvalidApiKeyException();
    }

    if (orderRequest.getBrothId() == null || orderRequest.getProteinId() == null) {
      throw new InvalidRequestException("both brothId and proteinId are required");
    }

    try {

      OrderResponseDTO responseDTO = service.placeOrder(orderRequest);

      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    } catch (Exception e) {

      logger.error("could not place order: {}", e.getMessage());

      throw new InternalServerErrorException("could not place order");

    }
  }

}
