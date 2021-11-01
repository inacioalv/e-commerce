package br.com.inacioalves.mc.product_catalog.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.exeption.objectNotFoundException;
import br.com.inacioalves.mc.product_catalog.mapper.ProductMapper;
import br.com.inacioalves.mc.product_catalog.model.Product;
import br.com.inacioalves.mc.product_catalog.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService service;
	
	private final ProductMapper productMapper = ProductMapper.INSTANCE;
	
	@Mock
	private ProductRepository repository;
	
	@Test
	public void whenPlantInformedThenItshouldBeCreated() {
		//given
		ProductDto expectedProductDto = createProductDto();
		Product expectedSavedProduct = productMapper.toModel(expectedProductDto);
		
		//when
		when(repository
				.findById(expectedProductDto.getId()))
				.thenReturn(Optional.empty());
		
		when(repository
				.save(expectedSavedProduct))
				.thenReturn(expectedSavedProduct);
		//then
		ProductDto createProductDto = 
				service.create(expectedProductDto);
		
		
		assertThat(createProductDto.getId(), 
				is(equalTo(createProductDto.getId())));
		
		assertThat(createProductDto.getName(), 
				is(equalTo(createProductDto.getName())));
		
		assertThat(createProductDto.getPrice(), 
				is(equalTo(createProductDto.getPrice())));
		
		assertThat(createProductDto.getDescription(), 
				is(equalTo(createProductDto.getDescription())));
		
		assertThat(createProductDto.getCategory(), 
				is(equalTo(createProductDto.getCategory())));
		
	}
	
	@Test
	public void  whenAlreadyRegisteredProductInformdThenAnExeptionShouldBeThrown() {
		//given
		ProductDto expectedProductDto = createProductDto();
		Product duplicatedProduct = productMapper.toModel(expectedProductDto);
	
		//when
		when(repository
					.findByName(expectedProductDto.getName()))
					.thenReturn(Optional.of(duplicatedProduct));
		//then
		assertThrows(objectNotFoundException.class, 
				()-> service.create(expectedProductDto));
		
		
	}
	
	@Test
	public void whenValidProductIsGivenThenReturnAProductById() {
		//given
		ProductDto expectedFoundProductDto = createProductDto();
		Product expectedFoundProduct = productMapper.toModel(expectedFoundProductDto);
		
		//when
		when(repository
					.findById(expectedFoundProduct.getId()))
					.thenReturn(Optional.of(expectedFoundProduct));
		//then
		ProductDto foundProductDto = 
					service.findById(expectedFoundProductDto.getId());
		
		assertThat(foundProductDto,
				is (equalTo(expectedFoundProductDto)));
	}
	
	@Test
	public void whenNotRegisteredPlnatByIdIsGivenThenThrowAnExeption() {
		//given
		ProductDto expectedFoundProductDto = createProductDto();
		
		//when
		when(repository
					.findById(expectedFoundProductDto.getId()))
					.thenReturn(Optional.empty());
		//then
		assertThrows(objectNotFoundException.class, 
						()-> service.findById(expectedFoundProductDto.getId()));
		
	}
	
	@Test
	public void whenListProductIsCalledThenReturnAListOfProduct() {
		//given
		ProductDto expectedFoundProductDto = createProductDto();
		Product expectedFoundProduct = productMapper.toModel(expectedFoundProductDto);
	
		//when
		when(repository.findAll())
			.thenReturn(Collections.singletonList(expectedFoundProduct));
		
		//then
		List<ProductDto> foundListProductDto = service.listAll();
		
		assertThat(foundListProductDto, is(not(empty())));
		assertThat(foundListProductDto.get(0), is(equalTo(expectedFoundProductDto)));
		
	}
	
	@Test
	public void whenListProductIsCalledThenReturnAnEmptyListOfProduct() {
		//when
		when(repository.findAll()).thenReturn(Collections.emptyList());
		
		//then
		List<ProductDto> foundListProductDto = service.listAll();
		
		assertThat(foundListProductDto, is(empty()));
	}
	
	@Test
	public void  whenExclusionIsCalledWithValidIdThenAProductShouldBeDeleted() {
		//given
		ProductDto expectedDeletedProductDto = createProductDto();
		Product expectedDeletedProduct = productMapper.toModel(expectedDeletedProductDto);
	
		//when
		when(repository
				   .findById(expectedDeletedProductDto.getId()))
				   .thenReturn(Optional.of(expectedDeletedProduct));
		doNothing().when(repository)
				   .deleteById(expectedDeletedProductDto.getId());
		
		//then
		service.deleteById(expectedDeletedProductDto.getId());
		
		verify(repository,times(1))
				.findById(expectedDeletedProductDto.getId());
		verify(repository,times(1))
				.deleteById(expectedDeletedProductDto.getId());
	}
	
	@Test
	public void WhenExclusionAndCalledWithmistakeattheIdentificationThenReturnsnotfound() {
		//given
		ProductDto expectedDeletedProductDto = createProductDto();
	
		//when
		when(repository
				   .findById(expectedDeletedProductDto.getId()))
				   .thenReturn(Optional.empty());
		
		//then
		assertThrows(objectNotFoundException.class, 
						()-> service.deleteById(expectedDeletedProductDto.getId()));
	}
	
	@Test
	public void WhenUpdateYourCalledWithValidHeMustThenUpdateProducts() {
		//given
		ProductDto expectedProductDto = createProductDto();
		expectedProductDto.setId(1L);
		Product expectedSavedProduct = productMapper.toModel(expectedProductDto);
				
		//when
		when(repository
					.findById(expectedSavedProduct.getId()))
					.thenReturn(Optional.of(expectedSavedProduct));
				
		when(repository
					.save(expectedSavedProduct))
					.thenReturn(expectedSavedProduct);
		//then
		ProductDto createProductDto = 
						service.updateById(expectedProductDto,1L);
				
				
				assertThat(createProductDto.getId(), 
						is(equalTo(createProductDto.getId())));
				
				assertThat(createProductDto.getName(), 
						is(equalTo(createProductDto.getName())));
				
				assertThat(createProductDto.getPrice(), 
						is(equalTo(createProductDto.getPrice())));
				
				assertThat(createProductDto.getDescription(), 
						is(equalTo(createProductDto.getDescription())));
				
				assertThat(createProductDto.getCategory(), 
						is(equalTo(createProductDto.getCategory())));
		
	}
	
	@Test
	public void WhenUpdateAndCalledWithErrorAt_IdentificationThenReturnsNotFound() {
		//given
		ProductDto expectedDeletedProductDto = createProductDto();
			
		//when
		when(repository
					.findById(expectedDeletedProductDto.getId()))
					.thenReturn(Optional.empty());
				
		//then
		assertThrows(objectNotFoundException.class, 
							()-> service.updateById(expectedDeletedProductDto,expectedDeletedProductDto.getId()));
		
	}
	
	public static ProductDto createProductDto() {
		return ProductDto.builder()
				.id(1L)
				.name("name")
				.price(1000)
				.description("description")
				.category("category")
				.availabity(true)
				.build();
	}

}
