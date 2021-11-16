package br.com.inacioalves.mc.orders_service.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;
import br.com.inacioalves.mc.orders_service.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;

	private Logger logger = LoggerFactory.getLogger(CartController.class);

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("createItem/{id}")
	public ResponseEntity<Cart> createItem(@PathVariable Long id, @RequestBody Product product) {

		logger.debug("Received request for cart by id: {}");
		Cart items = cartService.addToCart(null, id, product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(items.getId()).toUri();
		logger.debug("Cart: {}", items);
		return ResponseEntity.created(uri).body(items);
	}

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

	@GetMapping("/cart/{cartid}")
	public Cart getAllItemsFromCart(@PathVariable String cartid) {
		Cart items = cartService.getAllItemsFromCart(cartid);
		return items;
	}

}
