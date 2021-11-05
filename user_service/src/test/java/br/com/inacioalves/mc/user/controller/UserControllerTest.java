package br.com.inacioalves.mc.user.controller;

import static br.com.inacioalves.mc.user.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import br.com.inacioalves.mc.user.model.dto.UserDto;
import br.com.inacioalves.mc.user.service.UserService;
import br.com.inacioalves.mc.user.service.UserServiceTest;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	private static String API_URL_PATH="/user";

	private MockMvc mockMvc;
	
	@Mock
	private UserService service;
	
	@InjectMocks
	private UserController controller;
	
	@BeforeEach
	public void setup() {
		mockMvc= MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s,locale) -> new MappingJackson2JsonView())
				.build();
	}
	
	@Test
	public void whenPOSTIsCalledThenAUserIsCreated() throws Exception {
		UserDto userDto = UserServiceTest.createUserDto();
		
		//when
		when(service.create(userDto)).thenReturn(userDto);
		
		//then
		mockMvc.perform(post(API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.first_name",is(userDto.getFirst_name())))
				.andExpect(jsonPath("$.last_name",is(userDto.getLast_name())))
				.andExpect(jsonPath("$.email",is(userDto.getEmail())))
				.andExpect(jsonPath("$.nickname",is(userDto.getNickname())));
		
	}
	
	@Test
	public void  whenGETIsCalledWithValid_Id_ThenOkStatusIsReturned() throws Exception {
		UserDto userDto = UserServiceTest.createUserDto();
		
		//when
		when(service.findById(userDto.getId())).thenReturn(userDto);
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/"+userDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.first_name", is(userDto.getFirst_name())))
				.andExpect(jsonPath("$.email", is(userDto.getEmail())));
	}
	
	    
	
	@Test
	public void whenGetListIsCalledThenOkStatusIsReturned() throws Exception {
		//given
		UserDto userDto = UserServiceTest.createUserDto();
		
		//then
		when(service.listAll()).thenReturn(Collections.singletonList(userDto));
		
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].first_name", is(userDto.getFirst_name())))
				.andExpect(jsonPath("$[0].email", is(userDto.getEmail())));
	}
	
	@Test
	public void WhenTakeItListEmptyItReturnedOkStatus() throws Exception {
		//given
		UserDto userDto = UserServiceTest.createUserDto();
		
		//then
		when(service.listAll()).thenReturn(Collections.singletonList(userDto));
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void whenUpdateIsCalledThenOkStatusIsReturned() throws Exception {
		//given
		UserDto userDto = UserServiceTest.createUserDto();
		
		//then
		when(service.updateById(userDto, userDto.getId())).thenReturn(userDto);
		
		mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH+"/update"+"/"+userDto.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
				.andExpect(status().isNoContent());
				
	}
	
	
	
	@Test
	public void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
		//given
		UserDto userDto = UserServiceTest.createUserDto();
		
		//when
		doNothing().when(service).deleteById(userDto.getId());
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH+"/"+userDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNoContent());
	}
	

}
