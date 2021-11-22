package br.com.inacioalves.mc.orders_service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.inacioalves.mc.orders_service.feignclient.ProductClient;
import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;

@Repository
public class CartRepositoryImpl implements CartRepository {

	@Autowired
	private ProductClient productClient;

	@Autowired
	RedisTemplate<String, Cart> redisTemplate;

	private static final Logger log = LoggerFactory.getLogger(CartRepositoryImpl.class);

	@Override
	public Cart addToCart(String cardid, Long id, Product product) {
		double total = 0;
		Cart cart = null;
		product = productConversion(id);

		if ((cardid == null) || (cardid.equalsIgnoreCase(""))) {
			log.debug("Missing id, creating new cart.");
			cart = createCart(UUID.randomUUID().toString(), id, product);

		} else {
			cart = getCartById(cardid);
			log.debug("Retrieve existing cart by id: {}, cart: {}", id, cart);

			if (cart == null) {
				cart = createCart(cardid, id, product);
			} else {
				cart.getProduct().add(product);
			}
		}

		for (Product item : cart.getProduct()) {
			total += item.getPrice();
		}

		cart.setSubTotal(total);
		log.debug("cart: " + cart);
		redisTemplate.opsForValue().set(cart.getId(), cart);
		return cart;
	}

	@Override
	public Cart getCartById(String cartid) {
		return redisTemplate.opsForValue().get(cartid);
	}

	@Override
	public void deleteCart(String cartid) {
		redisTemplate.delete(cartid);
	}

	private Cart createCart(String cartid, Long id, Product product) {
		product = productConversion(id);

		List<Product> cartItems = new ArrayList<Product>();
		cartItems.add(product);

		Cart cart = new Cart();
		cart.setId(cartid);
		cart.setProduct(cartItems);
		cart.setSubTotal(product.getPrice());

		return cart;
	}

	private Product productConversion(Long id) {
		Product productConversion = productClient.getProductById(id);
		
		Product product = new Product(
				productConversion.getId(), 
				productConversion.getName(),
				productConversion.getPrice(), 
				productConversion.getDescription(), 
				productConversion.getCategory(),
				productConversion.getAvailabity(), 
				new Cart());
		
		return product;
	}

}