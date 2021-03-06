package br.com.inacioalves.mc.orders_service.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;
import br.com.inacioalves.mc.orders_service.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository repository;

	@Override
	public Cart getCart(String cartId) {
		Cart cart = repository.getCartById(cartId);
		return cart;
	}

	@Override
	public Cart addToCart(String cartid, Long id, Product product) {
		Cart items = repository.addToCart(cartid, id, product);
		return items;
	}

	@Override
	public void deleteCart(String cartId) {
		repository.deleteCart(cartId);
	}

	@Override
	public void removeProduct(String cartid, Long id) {
		repository.removeProduct(cartid, id);		
	}

	

	
	

}
