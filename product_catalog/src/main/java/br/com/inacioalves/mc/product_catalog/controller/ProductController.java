package br.com.inacioalves.mc.product_catalog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.exeption.objectNotFoundException;
import br.com.inacioalves.mc.product_catalog.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private ProductService service;

	public ProductController(ProductService service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
		return service.create(productDto);
	}
	
	@GetMapping("/all")
	public List<ProductDto> listAll(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Long id) throws objectNotFoundException {
		 return service.findById(id);
	 }
	
	 
	 @PutMapping("update/{id}")
	 @ResponseStatus(HttpStatus.OK)
	 public ResponseEntity<Void> UpdateProduct(@RequestBody ProductDto plantDto,@PathVariable Long id){
		 	plantDto.setId(id);
			service.updateById(plantDto,id);
			return ResponseEntity.noContent().build();
		}
	 
	 @DeleteMapping("/{id}")
	 @ResponseStatus(code = HttpStatus.NO_CONTENT)
	 public void deleteById(@PathVariable  Long id) throws objectNotFoundException {
		 service.deleteById(id);
		 
	 }

}
