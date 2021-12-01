package br.com.inacioalves.mc.orders_service.service;




import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;

public interface CartService {

	public Cart getCart(String cartId);
	Cart addToCart(String cardid, Long id, Product product);
	void deleteCart(String cardid);
	void removeProduct(String cartid,Long id);

}
