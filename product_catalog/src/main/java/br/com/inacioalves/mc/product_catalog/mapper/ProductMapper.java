package br.com.inacioalves.mc.product_catalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.model.Product;

@Mapper
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	Product toModel(ProductDto productDto);
	ProductDto tpDto(Product product);

}
