package br.com.inacioalves.mc.product_catalog.repository;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.inacioalves.mc.product_catalog.model.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ProductRepositoryTest {

	@Autowired
	ProductRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void must_persist_Product_in_the_database() {
		Product product = createProduct();
		product = repository.save(product);
		
		assertThat(product.getId()).isNotNull();
	}

	private Product createProduct() {
		return Product.builder()
				.id(1L)
				.name("name")
				.price(1000)
				.description("description")
				.category("category")
				.availabity(true)
				.build();
	}
}
