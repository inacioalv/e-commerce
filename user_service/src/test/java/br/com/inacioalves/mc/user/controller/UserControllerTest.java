package br.com.inacioalves.mc.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static br.com.inacioalves.mc.user.utils.JsonConvertionUtils.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

}
