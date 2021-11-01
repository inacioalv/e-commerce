package br.com.inacioalves.mc.product_catalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.exeption.objectNotFoundException;
import br.com.inacioalves.mc.product_catalog.mapper.ProductMapper;
import br.com.inacioalves.mc.product_catalog.model.Product;
import br.com.inacioalves.mc.product_catalog.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository repository;
	
	private final ProductMapper productMapper = ProductMapper.INSTANCE;
	

	public ProductService(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	public ProductDto create(ProductDto ProductDto)throws objectNotFoundException  {
		verifyIfIsAlreadyRegistered(ProductDto.getName());
		Product ProductSave = productMapper.toModel(ProductDto);
		Product saveProduct= repository.save(ProductSave);
		return productMapper.tpDto(saveProduct);
	}
	
	public List<ProductDto> listAll(){
		List<Product> allProduct = repository.findAll();
		
		return allProduct.stream()
				.map(productMapper::tpDto)
				.collect(Collectors.toList());
	}
	
	public ProductDto findById(Long id) throws objectNotFoundException {
		Product product =verifyIfExists(id);
		if(product.getQuantity() > 0) {
			product.setAvailabity(true);
		}
		return productMapper.tpDto(product);
	}
	

	public void deleteById(Long id) throws objectNotFoundException {
		verifyIfExists(id);
		
		repository.deleteById(id);
	}
	

	public ProductDto updateById(ProductDto ProductDto,Long id)  {
		verifyIfExists(id);
		Product ProductSave = productMapper.toModel(ProductDto);
		Product saveProduct= repository.save(ProductSave);
		return  productMapper.tpDto(saveProduct);
		
	}
	
	
	private Product verifyIfExists(Long id) throws objectNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new objectNotFoundException("Product not found with ID:"+id));
	}
	
	private void verifyIfIsAlreadyRegistered(String name) throws objectNotFoundException {
        Optional<Product> optSavedBeer = repository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new objectNotFoundException("Product with already existing name:"+name);
        }
    }

}
