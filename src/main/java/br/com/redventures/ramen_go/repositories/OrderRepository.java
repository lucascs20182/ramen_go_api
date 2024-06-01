package br.com.redventures.ramen_go.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.redventures.ramen_go.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  boolean existsByOrderId(String orderId);

}
