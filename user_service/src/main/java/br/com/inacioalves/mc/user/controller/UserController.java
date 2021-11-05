package br.com.inacioalves.mc.user.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.inacioalves.mc.user.exeption.objectNotFoundException;
import br.com.inacioalves.mc.user.model.dto.UserDto;
import br.com.inacioalves.mc.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(value = "Api Rest plant")
@CrossOrigin(origins = "*")
public class UserController {
	
	private UserService service;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create User")
	public ResponseEntity<UserDto>  create(@RequestBody UserDto userDto) {
		UserDto obj = service.create(userDto);
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return  ResponseEntity.created(uri).body(obj);
	
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "search user")
	public ResponseEntity<List<UserDto>> listAll(){
		logger.info("retrieveExchangeValue called with");
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
	@ApiOperation(value = "search user by id")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) throws objectNotFoundException {
		 logger.info("retrieveExchangeValue called with {}",id);
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
	 @ApiOperation(value = "Update user")
	 public ResponseEntity<Void> Update(@RequestBody UserDto userDto,@PathVariable Long id){
		 	userDto.setId(id);
			service.updateById(userDto,id);
			return ResponseEntity.noContent().build();
		}
	 
	 @DeleteMapping("/{id}")
	 @ResponseStatus(code = HttpStatus.NO_CONTENT)
	 @ApiOperation(value = "Delete user")
	 public ResponseEntity<Void>  deleteById(@PathVariable  Long id) throws objectNotFoundException {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	 }
}
