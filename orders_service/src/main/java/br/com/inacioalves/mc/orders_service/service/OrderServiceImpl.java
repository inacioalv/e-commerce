package br.com.inacioalves.mc.orders_service.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.inacioalves.mc.orders_service.exeption.objectNotFoundException;
import br.com.inacioalves.mc.orders_service.mapper.OrderMapper;
import br.com.inacioalves.mc.orders_service.model.OrderCart;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;
import br.com.inacioalves.mc.orders_service.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository repository;
	
	private final OrderMapper orderMapper = OrderMapper.INSTANCE;

	public OrderServiceImpl(OrderRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public OrderCartDto saveOrder(OrderCartDto orderCartDto) {
		OrderCart OrderSave = orderMapper.toModel(orderCartDto);
		OrderCart saveOrder= repository.save(OrderSave);
		return orderMapper.tpDto(saveOrder);
	}
	
	@Override
	public OrderCartDto findById(Long id) throws objectNotFoundException {
		OrderCart order =verifyIfExists(id);
		return orderMapper.tpDto(order);
	}
	
	@Override
	public List<OrderCartDto> listAll(){
		List<OrderCart> allOrder = repository.findAll();
		
		return allOrder.stream()
				.map(orderMapper :: tpDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Long id) throws objectNotFoundException {
		verifyIfExists(id);
		repository.deleteById(id);
	}


	private OrderCart verifyIfExists(Long id) throws objectNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new objectNotFoundException("Order not found with ID:"+id));
	}

}
