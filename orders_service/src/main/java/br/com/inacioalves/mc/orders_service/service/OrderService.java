package br.com.inacioalves.mc.orders_service.service;

import java.util.List;

import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;

public interface OrderService {
	
	public OrderCartDto saveOrder(OrderCartDto orderCartDto); 
	public OrderCartDto findById(Long id);
	public void delete(Long id);
	public List<OrderCartDto> listAll();

}
