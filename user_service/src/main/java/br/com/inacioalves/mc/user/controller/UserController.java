package br.com.inacioalves.mc.user.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacioalves.mc.user.exeption.objectNotFoundException;
import br.com.inacioalves.mc.user.model.dto.UserDto;
import br.com.inacioalves.mc.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDto>  create(@RequestBody UserDto userDto) {
		UserDto obj = service.create(userDto);
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return  ResponseEntity.created(uri).body(obj);
	
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> listAll(){
		List<UserDto> userDto = service.listAll();
		
		if(!userDto.isEmpty()) {
			return new ResponseEntity<List<UserDto>>(
					 userDto,
					 HttpStatus.OK);
		}
		
		return new ResponseEntity<List<UserDto>>(
				HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) throws objectNotFoundException {
		 UserDto userDto = service.findById(id);
		 
		 if(userDto !=null) {
			 return new ResponseEntity<UserDto>(
					 userDto,
					 HttpStatus.OK);
		 }
		
		return new ResponseEntity<UserDto>(
				HttpStatus.NOT_FOUND);
	 }
	
	 @PutMapping("update/{id}")
	 @ResponseStatus(HttpStatus.OK)
	 public ResponseEntity<Void> Update(@RequestBody UserDto userDto,@PathVariable Long id){
		 	userDto.setId(id);
			service.updateById(userDto,id);
			return ResponseEntity.noContent().build();
		}
	 
	 @DeleteMapping("/{id}")
	 @ResponseStatus(code = HttpStatus.NO_CONTENT)
	 public ResponseEntity<Void>  deleteById(@PathVariable  Long id) throws objectNotFoundException {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	 }
}
