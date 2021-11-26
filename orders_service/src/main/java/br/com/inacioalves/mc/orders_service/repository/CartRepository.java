package br.com.inacioalves.mc.orders_service.repository;



import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;

public interface CartRepository  {
	
	Cart addToCart(String cardid,Long id, Product product );
	Cart getCartById(String cartid);
	void deleteCart(String cartid);
	void removeProduct(String cartid,Long id);

}
