package br.com.inacioalves.mc.orders_service.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.inacioalves.mc.orders_service.enums.Status;
import br.com.inacioalves.mc.orders_service.exeption.objectNotFoundException;
import br.com.inacioalves.mc.orders_service.feignclient.UserClient;
import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.OrderCart;
import br.com.inacioalves.mc.orders_service.model.User;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;
import br.com.inacioalves.mc.orders_service.service.CartService;
import br.com.inacioalves.mc.orders_service.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(value = "Api Rest Order")
@CrossOrigin(origins = "*")
public class OrderController {

	private OrderService service;

	@Autowired
	private UserClient userClient;

	@Autowired
	private CartService cartService;

	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@ApiOperation(value = "Create Order")
	@PostMapping("/user/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<OrderCartDto> saveOrder(OrderCartDto orderDto, @PathVariable Long id,
			@RequestHeader(value = "Cookie") String cartid) throws objectNotFoundException {

		List<Cart> listcart = new ArrayList<Cart>();
		Cart cart = cartService.getCart(cartid);
		listcart.add(cart);
		User user = getUserfindById(id);

		if (cart != null && user != null) {
			orderDto = createOrder(orderDto, user, listcart);
			orderDto.setStatus(Status.CONFIRMED);
			try {
				service.saveOrder(orderDto);
				cartService.deleteCart(cartid);
				return new ResponseEntity<OrderCartDto>(orderDto, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				orderDto.setStatus(Status.CANCELLED);
				return new ResponseEntity<OrderCartDto>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		orderDto.setStatus(Status.CANCELLED);
		return new ResponseEntity<OrderCartDto>(HttpStatus.NOT_FOUND);

	}

	@ApiOperation(value = "Search order")
	@GetMapping("/{id}")
	public ResponseEntity<OrderCartDto> findById(@PathVariable Long id) throws objectNotFoundException {
		OrderCartDto orderDto = service.findById(id);

		if (orderDto != null) {
			return new ResponseEntity<OrderCartDto>(orderDto, HttpStatus.OK);
		}

		return new ResponseEntity<OrderCartDto>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Search Order all")
	@GetMapping("/all")
	public ResponseEntity<List<OrderCartDto>> listAll() {
		List<OrderCartDto> listOrder = service.listAll();

		if (!listOrder.isEmpty()) {
			return new ResponseEntity<List<OrderCartDto>>(listOrder, HttpStatus.OK);
		}
		return new ResponseEntity<List<OrderCartDto>>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Delete Order")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	private OrderCartDto createOrder(OrderCartDto orderDto, User user, List<Cart> listcart) {
		orderDto.setOrderedDate(LocalDateTime.now());
		orderDto.setUser(user);
		orderDto.setCart(listcart);
		return orderDto;
	}

	public User getUserfindById(Long id) {
		User userConversion = userClient.getUserById(id);
		List<OrderCart> list = new ArrayList<OrderCart>();

		return new User(userConversion.getId(), userConversion.getFirst_name(), userConversion.getLast_name(),
				userConversion.getEmail(), userConversion.getNickname(), userConversion.getPhone(), list);
	}

}
