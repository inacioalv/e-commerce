package br.com.inacioalves.mc.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.inacioalves.mc.user.model.User;
import br.com.inacioalves.mc.user.model.dto.UserDto;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User toModel(UserDto userDto);
	UserDto tpDto(User user);

}
