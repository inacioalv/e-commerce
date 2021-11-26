package br.com.inacioalves.mc.orders_service.controller;



import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;
import br.com.inacioalves.mc.orders_service.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/cart")
@Api(value = "Api Rest Order")
@CrossOrigin(origins = "*")
public class CartController {
	
	@Autowired
	CartService cartService;

	private Logger logger = LoggerFactory.getLogger(CartController.class);

	@ApiOperation(value = "Create cart with id null")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("createItem/{id}")
	public ResponseEntity<Cart> createItem(@PathVariable Long id, @RequestBody Product product) {

		logger.debug("Received request for cart by id: {}");
		Cart items = cartService.addToCart(null, id, product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(items.getId()).toUri();
		logger.debug("Cart: {}", items);
		return ResponseEntity.created(uri).body(items);
	}

	@ApiOperation(value = "Create cart")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("createItem/{cartid}/{id}")
	public ResponseEntity<Cart> createItem(@PathVariable String cartid, @PathVariable Long id,
			@RequestBody Product product) {

		logger.debug("Received request for cart by id: {}", cartid);
		Cart items = cartService.addToCart(cartid, id, product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(items.getId()).toUri();
		logger.debug("Cart: {}", items);
		return ResponseEntity.created(uri).body(items);
	}

	@ApiOperation(value = "Search cart")
	@GetMapping("/{cartid}")
	public Cart getAllItemsFromCart(@PathVariable String cartid) {
		Cart items = cartService.getAllItemsFromCart(cartid);
		return items;
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<Object> delete(@PathVariable String cartId) {
		cartService.deleteCart(cartId);
		return ResponseEntity.noContent().build();
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/remove/cart/{cartid}/product/{id}")
	public void remove(@PathVariable String cartid,@PathVariable Long id) {
		cartService.removeProduct(cartid, id);
	}
	
	
}

