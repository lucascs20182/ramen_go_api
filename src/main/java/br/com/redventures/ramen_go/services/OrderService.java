package br.com.redventures.ramen_go.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.redventures.ramen_go.dtos.OrderRequestDTO;
import br.com.redventures.ramen_go.dtos.OrderResponseDTO;
import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.entities.OrderEntity;
import br.com.redventures.ramen_go.entities.ProteinEntity;
import br.com.redventures.ramen_go.exceptions.InvalidRequestException;
import br.com.redventures.ramen_go.repositories.BrothRepository;
import br.com.redventures.ramen_go.repositories.OrderRepository;
import br.com.redventures.ramen_go.repositories.ProteinRepository;

@Service
public class OrderService {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private BrothRepository brothRepository;

  @Autowired
  private ProteinRepository proteinRepository;

  public List<OrderEntity> listAll() {
    return repository.findAll();
  }

  public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {

    BrothEntity brothEntity = brothRepository
      .findById(Long.valueOf(orderRequest.getBrothId()))
      .orElseThrow(() -> new InvalidRequestException("Invalid broth ID"));

    ProteinEntity proteinEntity = proteinRepository
      .findById(Long.valueOf(orderRequest.getProteinId()))
      .orElseThrow(() -> new InvalidRequestException("Invalid protein ID"));

    String description = brothEntity.getName()
      + " and " + proteinEntity.getName();

    OrderEntity orderEntity = new OrderEntity();

    orderEntity.setBroth(brothEntity);
    orderEntity.setProtein(proteinEntity);
    orderEntity.setOrderId(generateUniqueOrderId());
    orderEntity.setDescription(description);
    orderEntity.setImage("https://tech.redventures.com.br/icons/ramen/ramenChasu.png");

    OrderEntity savedOrderEntity = repository.save(orderEntity);

    OrderResponseDTO orderResponse = new OrderResponseDTO();

    orderResponse.setId(savedOrderEntity.getOrderId());
    orderResponse.setDescription(savedOrderEntity.getDescription());
    orderResponse.setImage(savedOrderEntity.getImage());

    return orderResponse;
  }

  private String generateUniqueOrderId() {

    String orderId;
    Random random = new Random();

    do {
      // Generate a random number between 000000 and 999999
      orderId = String.format("%06d", random.nextInt(1000000));
    } while (repository.existsByOrderId(orderId));

    return orderId;
  }

}
