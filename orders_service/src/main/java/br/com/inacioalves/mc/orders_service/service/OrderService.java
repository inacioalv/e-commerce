package br.com.inacioalves.mc.orders_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inacioalves.mc.orders_service.exeption.objectNotFoundException;
import br.com.inacioalves.mc.orders_service.feignclient.UserClient;
import br.com.inacioalves.mc.orders_service.mapper.OrderMapper;
import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.OrderCart;
import br.com.inacioalves.mc.orders_service.model.User;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;
import br.com.inacioalves.mc.orders_service.repository.OrderRepository;

@Service
public class OrderService {
	
	private OrderRepository repository;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private CartService cartService;
	
	private final OrderMapper orderMapper = OrderMapper.INSTANCE;

	public OrderService(OrderRepository repository) {
		super();
		this.repository = repository;
	}
	
	
	public OrderCartDto create(OrderCartDto orderDto,Long id,String cartid)throws objectNotFoundException  {
		User user = buscarUser(id);
		orderDto.setOrderedDate(LocalDateTime.now());
		orderDto.setUser(user);
		
		List<Cart> listcart = new ArrayList<Cart>();
		Cart cart = cartService.getAllItemsFromCart(cartid);
		listcart.add(cart);
		orderDto.setCart(listcart);
		
		OrderCart OrderSave = orderMapper.toModel(orderDto);
		OrderCart saveOrder= repository.save(OrderSave);
		return orderMapper.tpDto(saveOrder);
	}
	
	public OrderCartDto findById(Long id) throws objectNotFoundException {
		OrderCart order =verifyIfExists(id);
		return orderMapper.tpDto(order);
	}
	
	public User buscarUser(Long id) {
		User  userConversion = userClient.getUserById(id);
		List<OrderCart> list = new ArrayList<OrderCart>();
		
		return new User(
				userConversion.getId(),
				userConversion.getFirst_name(),
				userConversion.getLast_name(),
				userConversion.getEmail(),
				userConversion.getNickname(),
				userConversion.getPhone(),
				list
				);
	}


	private OrderCart verifyIfExists(Long id) throws objectNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new objectNotFoundException("Order not found with ID:"+id));
	}

}
