package br.com.inacioalves.mc.user.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.inacioalves.mc.user.exeption.objectNotFoundException;
import br.com.inacioalves.mc.user.mapper.UserMapper;
import br.com.inacioalves.mc.user.model.User;
import br.com.inacioalves.mc.user.model.dto.PhoneDto;
import br.com.inacioalves.mc.user.model.dto.UserDto;
import br.com.inacioalves.mc.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {
	
	@InjectMocks
	private UserService service;
	
	private final UserMapper userMapper = UserMapper.INSTANCE;
	
	@Mock
	private UserRepository repository;
	
	
	@Test
	public void whenUserInformedThenItshouldBeCreated() {
		//given
		UserDto expectedUserDto = createUserDto();
		User expectedSavedUser = userMapper.toModel(expectedUserDto);
		
		//when
		when(repository
				.findById(expectedUserDto.getId()))
				.thenReturn(Optional.empty());
		
		when(repository
				.save(expectedSavedUser))
				.thenReturn(expectedSavedUser);
		//then
		UserDto createUserDto = 
				service.create(expectedUserDto);
		
		
		assertThat(createUserDto.getId(), 
				is(equalTo(createUserDto.getId())));
		
		assertThat(createUserDto.getFirst_name(), 
				is(equalTo(createUserDto.getFirst_name())));
		
		assertThat(createUserDto.getEmail(), 
				is(equalTo(createUserDto.getEmail())));
		
	}
	
	@Test
	public void  WhenAlreadyRegisteredInformThenAExeptionShouldBeThrown() {
		//given
		UserDto expectedUserDto = createUserDto();
		User duplicatedUser  = userMapper.toModel(expectedUserDto);

	
		//when
		when(repository
					.findByNickname(expectedUserDto.getNickname()))
					.thenReturn(Optional.of(duplicatedUser));
		//then
		assertThrows(objectNotFoundException.class, 
				()-> service.create(expectedUserDto));
		
		
	}
	
	@Test
	public void WhenListITCalledThenTurnBackAnFindAll() {
		//given
		UserDto expectedFoundUserDto = createUserDto();
		User expectedFoundUser = userMapper.toModel(expectedFoundUserDto);
	
		//when
		when(repository.findAll())
			.thenReturn(Collections.singletonList(expectedFoundUser));
		
		//then
		List<UserDto> foundListUserDto = service.listAll();
		
		assertThat(foundListUserDto, is(not(empty())));
		assertThat(foundListUserDto.get(0), is(equalTo(expectedFoundUserDto)));
		
	}
	
	@Test
	public void WhenListIsCalledThenReturnEmptyList() {
		//when
		when(repository.findAll()).thenReturn(Collections.emptyList());
		
		//then
		List<UserDto> foundListUserDto = service.listAll();
		
		assertThat(foundListUserDto, is(empty()));
	}
	
	@Test
	public void WhenYourCalledWithValidHeMustThenUpdate() {
		//given
		UserDto expectedUserDto = createUserDto();
		User expectedSavedUser = userMapper.toModel(expectedUserDto);
				
		//when
		when(repository
					.findById(expectedSavedUser.getId()))
					.thenReturn(Optional.of(expectedSavedUser));
				
		when(repository
					.save(expectedSavedUser))
					.thenReturn(expectedSavedUser);
		//then
		UserDto createUserDto = 
						service.updateById(expectedUserDto,1L);
				
				
				assertThat(createUserDto.getId(), 
						is(equalTo(createUserDto.getId())));
				
				assertThat(createUserDto.getFirst_name(), 
						is(equalTo(createUserDto.getFirst_name())));
				
				assertThat(createUserDto.getEmail(), 
						is(equalTo(createUserDto.getEmail())));
		
	}
	
	@Test
	public void WhenUpdateAndCalledWithErrorAtIdentificationThenReturnsNotFound() {
		//given
		UserDto expectedDeletedUserDto = createUserDto();
			
		//when
		when(repository
					.findById(expectedDeletedUserDto.getId()))
					.thenReturn(Optional.empty());
				
		//then
		assertThrows(objectNotFoundException.class, 
							()-> service.updateById(expectedDeletedUserDto,expectedDeletedUserDto.getId()));
		
	}
	
	@Test
	public void  whenExclusionIsCalledWithValidIdThenShouldBeDeleted() {
		//given
		UserDto expectedDeletedUserDto = createUserDto();
		User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDto);
	
		//when
		when(repository
				   .findById(expectedDeletedUserDto.getId()))
				   .thenReturn(Optional.of(expectedDeletedUser));
		doNothing().when(repository)
				   .deleteById(expectedDeletedUserDto.getId());
		
		//then
		service.deleteById(expectedDeletedUserDto.getId());
		
		verify(repository,times(1))
				.findById(expectedDeletedUserDto.getId());
		verify(repository,times(1))
				.deleteById(expectedDeletedUserDto.getId());
	}
	
	@Test
	public void WhenExclusionAndCalledWithMistakeAtIdentificationThenReturnsNotFound() {
		//given
		UserDto expectedDeletedUserDto = createUserDto();
	
		//when
		when(repository
				   .findById(expectedDeletedUserDto.getId()))
				   .thenReturn(Optional.empty());
		
		//then
		assertThrows(objectNotFoundException.class, 
						()-> service.deleteById(expectedDeletedUserDto.getId()));
	}
	


	public static UserDto createUserDto() {
		return UserDto.builder()
				.id(1L)
				.first_name("first_name")
				.last_name("last_name")
				.email("email")
				.nickname("nickname")
				.phone(new PhoneDto("10",10,true))
				.build();
	}

}
