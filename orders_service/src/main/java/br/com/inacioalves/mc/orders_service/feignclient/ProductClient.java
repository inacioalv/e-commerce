package br.com.inacioalves.mc.orders_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.inacioalves.mc.orders_service.model.Product;

@FeignClient(name="product")
public interface ProductClient {
	
	 @GetMapping(value = "/product/{id}")
	 public Product getProductById(@PathVariable Long id);

}
