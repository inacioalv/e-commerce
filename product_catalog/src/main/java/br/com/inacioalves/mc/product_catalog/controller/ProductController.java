package br.com.inacioalves.mc.product_catalog.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.exeption.objectNotFoundException;
import br.com.inacioalves.mc.product_catalog.responses.Response;
import br.com.inacioalves.mc.product_catalog.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api(value = "Api Rest plant")
@CrossOrigin(origins = "*")
public class ProductController {
	
	private ProductService service;

	public ProductController(ProductService service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create product")
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto,BindingResult result) {
		Response<ProductDto> response = new Response<ProductDto>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors()
					.add(error.getDefaultMessage()));
		}
		
		ProductDto productSave = service.create(productDto);
		URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productSave.getId())
				.toUri();
		
		response.setData(productSave);
		
		return ResponseEntity.created(location).body(productSave);
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "List product")
	public List<ProductDto> listAll(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get By ID")
	public ProductDto findById(@PathVariable Long id) throws objectNotFoundException {
		 return service.findById(id);
	 }
	
	 
	 @PutMapping("update/{id}")
	 @ResponseStatus(HttpStatus.OK)
	 @ApiOperation(value = "Update product")
	 public ResponseEntity<Void> UpdateProduct(@RequestBody ProductDto plantDto,@PathVariable Long id){
		 	plantDto.setId(id);
			service.updateById(plantDto,id);
			return ResponseEntity.noContent().build();
		}
	 
	 @DeleteMapping("/{id}")
	 @ResponseStatus(code = HttpStatus.NO_CONTENT)
	 @ApiOperation(value = "delete product")
	 public ResponseEntity<Void>  deleteById(@PathVariable  Long id) throws objectNotFoundException {
		 service.deleteById(id);
		 return ResponseEntity.noContent().build();
	 }

}
