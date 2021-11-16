package br.com.inacioalves.mc.orders_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.inacioalves.mc.orders_service.model.User;

@FeignClient(name="user")
public interface UserClient {
	
	@GetMapping(value = "/user/{id}")
	public User getUserById(@PathVariable Long id);

}
