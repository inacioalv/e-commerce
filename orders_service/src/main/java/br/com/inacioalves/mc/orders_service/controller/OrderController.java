package br.com.inacioalves.mc.orders_service.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacioalves.mc.orders_service.exeption.objectNotFoundException;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;
import br.com.inacioalves.mc.orders_service.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(value = "Api Rest Order")
@CrossOrigin(origins = "*")
public class OrderController {

	private OrderService service;

	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@ApiOperation(value = "Create Order")
	@PostMapping("user/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<OrderCartDto> create(
			OrderCartDto orderDto,
			@PathVariable Long id,
			@RequestHeader(value = "Cookie") String cartid) {
		
		OrderCartDto obj = service.create(orderDto,id,cartid);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

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
	public ResponseEntity<List<OrderCartDto>> listAll(){
		List<OrderCartDto> listOrder = service.listAll();
		
		if(!listOrder.isEmpty()) {
			return new ResponseEntity<List<OrderCartDto>>(
					 listOrder,
					 HttpStatus.OK);
		}
		return new ResponseEntity<List<OrderCartDto>>(
				 HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Delete Order")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	


}
