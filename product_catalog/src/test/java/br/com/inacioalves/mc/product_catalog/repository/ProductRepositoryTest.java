package br.com.inacioalves.mc.product_catalog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

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
	
	
	@Test
	public void returnProductFindById() {
		Product product =createPersistirTheProduct();
		
		Optional<Product> plantfound = repository.findById(product.getId());
		
		assertThat(plantfound.isPresent()).isTrue();
	}
	
	@Test
	public void returnDeleteProduct() {
		Product product =createPersistirTheProduct();
		
		product= entityManager.find(Product.class, product.getId());
		
		repository.delete(product);
		
		Product plantNonexistent = entityManager.find(Product.class, product.getId());
		assertThat(plantNonexistent).isNull();
	}
	
	@Test
	public void returnUpdattePlant() {
		Product product =createPersistirTheProduct();
		
		product.setName("name");
		product.setPrice(1000);
		product.setDescription("description");
		product.setCategory("category");
		repository.save(product);
		
		Product productUpdated = entityManager.find(Product.class, product.getId());
		
		assertThat(productUpdated.getName()).isEqualTo("name");
		assertThat(productUpdated.getPrice()).isEqualTo(1000);
		assertThat(productUpdated.getDescription()).isEqualTo("description");
		assertThat(productUpdated.getCategory()).isEqualTo("category");
		
		
	}
	
	private Product createPersistirTheProduct() {
		Product product = createProduct();
		entityManager.persist(product);
		return product;
	}
	
	
	
	

	private Product createProduct() {
		return Product.builder()
				.name("name")
				.price(1000)
				.description("description")
				.category("category")
				.availabity(10)
				.build();
	}
}
