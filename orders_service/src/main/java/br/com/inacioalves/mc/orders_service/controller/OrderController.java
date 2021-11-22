package br.com.inacioalves.mc.orders_service.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacioalves.mc.orders_service.exeption.objectNotFoundException;
import br.com.inacioalves.mc.orders_service.model.User;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;
import br.com.inacioalves.mc.orders_service.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService service;

	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@PostMapping("user/{id}/cart/{cartid}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<OrderCartDto> create(@RequestBody OrderCartDto orderDto,@PathVariable Long id,@PathVariable String cartid) {
		OrderCartDto obj = service.create(orderDto,id,cartid);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderCartDto> findById(@PathVariable Long id) throws objectNotFoundException {
		OrderCartDto orderDto = service.findById(id);

		if (orderDto != null) {
			return new ResponseEntity<OrderCartDto>(orderDto, HttpStatus.OK);
		}

		return new ResponseEntity<OrderCartDto>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/user/{id}")
	public User buscarUser(@PathVariable Long id) {
		return service.buscarUser(id);
	}

}