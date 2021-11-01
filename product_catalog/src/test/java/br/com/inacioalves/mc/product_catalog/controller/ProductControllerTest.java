package br.com.inacioalves.mc.product_catalog.controller;

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

import static br.com.inacioalves.mc.product_catalog.utils.JsonConvertionUtils.asJsonString;
import br.com.inacioalves.mc.product_catalog.dto.ProductDto;
import br.com.inacioalves.mc.product_catalog.service.ProductService;
import br.com.inacioalves.mc.product_catalog.service.ProductServiceTest;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	
	private static String API_URL_PATH="/product";

	private MockMvc mockMvc;
	
	@Mock
	private ProductService service;
	
	@InjectMocks
	private ProductController controller;
	
	@BeforeEach
	public void setup() {
		mockMvc= MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s,locale) -> new MappingJackson2JsonView())
				.build();
	}
	
	@Test
	public void whenPOSTIsCalledThenAProductIsCreated() throws Exception {
		ProductDto productDto = ProductServiceTest.createProductDto();
		
		//when
		when(service.create(productDto)).thenReturn(productDto);
		
		
		//then
		mockMvc.perform(post(API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name",is(productDto.getName())))
				.andExpect(jsonPath("$.description",is(productDto.getDescription())))
				.andExpect(jsonPath("$.category",is(productDto.getCategory())));
		
	}
	
	@Test
	public void  whenGETIsCalledWithValid_Id_ThenOkStatusIsReturned() throws Exception {
		ProductDto productDto = ProductServiceTest.createProductDto();
		
		//when
		when(service.findById(productDto.getId())).thenReturn(productDto);
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/"+productDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(productDto.getName())))
				.andExpect(jsonPath("$.description", is(productDto.getDescription())));
	}
	
	@Test
	public void WhenSearchListHeMustReturnedOkStatus() throws Exception {
		//given
		ProductDto productDto = ProductServiceTest.createProductDto();
		
		//then
		when(service.listAll()).thenReturn(Collections.singletonList(productDto));
		
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(productDto.getName())))
				.andExpect(jsonPath("$[0].description", is(productDto.getDescription())));
	}
	
	
	@Test
	public void WhenTakeItListEmptyItIsReturnedOKStatus() throws Exception {
		//given
		ProductDto productDto = ProductServiceTest.createProductDto();
		
		//then
		when(service.listAll()).thenReturn(Collections.singletonList(productDto));
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH+"/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	

	@Test
	public void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
		//given
		ProductDto productDto = ProductServiceTest.createProductDto();
		
		//when
		doNothing().when(service).deleteById(productDto.getId());
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH+"/"+productDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNoContent());
	}
	

}
