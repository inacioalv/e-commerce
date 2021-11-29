package br.com.inacioalves.mc.orders_service.service;

import java.util.List;

import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;

public interface OrderService {
	
	public OrderCartDto create(OrderCartDto orderDto,Long id,String cartid);
	public OrderCartDto findById(Long id);
	public void delete(Long id);
	public List<OrderCartDto> listAll();

}
