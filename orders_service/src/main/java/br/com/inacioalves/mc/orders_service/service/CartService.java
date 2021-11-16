package br.com.inacioalves.mc.orders_service.service;



import br.com.inacioalves.mc.orders_service.model.Cart;
import br.com.inacioalves.mc.orders_service.model.Product;

public interface CartService {
	
//	    public Order_items addItemToCart(Order_items items,Long id);
	    public Cart getAllItemsFromCart(String cartid);
	    Cart addToCart(String cardid,Long id, Product product );
	    void deleteCart(String cardid);
	   
	   
}
