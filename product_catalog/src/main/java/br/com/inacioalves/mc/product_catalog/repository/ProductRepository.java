package br.com.inacioalves.mc.product_catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inacioalves.mc.product_catalog.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByName(String name);

}
