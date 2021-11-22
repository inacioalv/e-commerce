package br.com.inacioalves.mc.orders_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inacioalves.mc.orders_service.model.Teste;
import br.com.inacioalves.mc.orders_service.repository.TesteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	private TesteRepository  repository;

	public TesteController(TesteRepository repository) {
		super();
		this.repository = repository;
	}
	
	
	@PostMapping
	public Teste createTeste(@RequestBody Teste teste) {
		return repository.save(teste);
	}

}
