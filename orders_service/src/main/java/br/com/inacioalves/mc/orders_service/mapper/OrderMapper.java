package br.com.inacioalves.mc.orders_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.inacioalves.mc.orders_service.model.OrderCart;
import br.com.inacioalves.mc.orders_service.model.dto.OrderCartDto;

@Mapper
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	OrderCart toModel(OrderCartDto orderDto);

	OrderCartDto tpDto(OrderCart order);

}
