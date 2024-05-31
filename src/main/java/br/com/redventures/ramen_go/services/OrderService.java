package br.com.redventures.ramen_go.services;

import java.util.List;

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

    OrderEntity orderEntity = new OrderEntity();

    orderEntity.setBroth(brothEntity);
    orderEntity.setProtein(proteinEntity);

    OrderEntity savedOrderEntity = repository.save(orderEntity);

    OrderResponseDTO orderResponse = new OrderResponseDTO();
    orderResponse.setOrderId(savedOrderEntity.getId());

    return orderResponse;
  }

}
