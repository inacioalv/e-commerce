package br.com.inacioalves.mc.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.inacioalves.mc.user.exeption.objectNotFoundException;
import br.com.inacioalves.mc.user.mapper.UserMapper;
import br.com.inacioalves.mc.user.model.User;
import br.com.inacioalves.mc.user.model.dto.UserDto;
import br.com.inacioalves.mc.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository repository;
	
	private final UserMapper userMapper = UserMapper.INSTANCE;

	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}
	
	public UserDto create(UserDto plantDto)throws objectNotFoundException  {
		verifyIfIsAlreadyRegistered(plantDto.getNickname());
		User plantSave = userMapper.toModel(plantDto);
		User savePlant= repository.save(plantSave);
		return userMapper.tpDto(savePlant);
	}
	
	
	public List<UserDto> listAll(){
		List<User> alluser = repository.findAll();
		
		return alluser.stream()
				.map(userMapper::tpDto)
				.collect(Collectors.toList());
	}
	
	public UserDto findById(Long id) throws objectNotFoundException {
		User plant =verifyIfExists(id);
		return userMapper.tpDto(plant);
	}
	
	public UserDto updateById(UserDto plantDto,Long id)  {
		verifyIfExists(id);
		User userSave = userMapper.toModel(plantDto);
		User saveUser= repository.save(userSave);
		return  userMapper.tpDto(saveUser);
		
	}
	
	public void deleteById(Long id) throws objectNotFoundException {
		verifyIfExists(id);
		repository.deleteById(id);
	}
	
	private User verifyIfExists(Long id) throws objectNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new objectNotFoundException("User not found with ID:"+id));
	}
	

	private void verifyIfIsAlreadyRegistered(String nickname) {
		 Optional<User> optSavedBeer = repository.findByNickname(nickname);
	        if (optSavedBeer.isPresent()) {
	            throw new objectNotFoundException("Plant with already existing name:"+nickname);
	        }
		
	}
	
	

}
