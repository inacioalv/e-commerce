package br.com.inacioalves.mc.orders_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inacioalves.mc.orders_service.model.OrderCart;

public interface OrderRepository extends JpaRepository<OrderCart, Long>{

}
