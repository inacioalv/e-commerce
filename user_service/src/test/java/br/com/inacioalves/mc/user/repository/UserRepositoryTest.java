package br.com.inacioalves.mc.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.inacioalves.mc.user.model.Phone;
import br.com.inacioalves.mc.user.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void must_persist_User_in_the_database() {
		User user = createUser();
		user = repository.save(user);
		
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void must_check_the_existence_User() {
		User plant = createUser();
		entityManager.merge(plant);
		
		Optional<User> userfound = repository.findById(plant.getId());
		
		assertThat(userfound.isPresent()).isTrue();
	
	}
	
	@Test
	public void return_When_is_no_registered_withId() {
		Optional<User> userfound = repository.findById(1L);
		
		assertThat(userfound.isPresent()).isFalse();
	}
	
	@Test
	public void returnFindById() {
		User user = createPersistirTheUser();
		
		Optional<User> userfound = repository.findById(user.getId());
		
		assertThat(userfound.isPresent()).isTrue();
	}
	
	@Test
	public void returnDelete() {
		User user = createPersistirTheUser();
		
		user = entityManager.find(User.class, user.getId());
		
		repository.delete(user);
		
		User userNonexistent = entityManager.find(User.class, user.getId());
		assertThat(userNonexistent).isNull();
	}
	
	@Test
	public void returnUpdatte() {
		User user = createPersistirTheUser();
		
		user.setFirst_name("first_name");
		user.setLast_name("last_name");
		user.setEmail("email");
		
		repository.save(user);
		
		User userUpdated = entityManager.find(User.class, user.getId());
		
		assertThat(userUpdated.getFirst_name()).isEqualTo("first_name");
		assertThat(userUpdated.getLast_name()).isEqualTo("last_name");
		assertThat(userUpdated.getEmail()).isEqualTo("email");
		
		
	}
	
	
	private User createPersistirTheUser() {
		User user = createUser();
		entityManager.merge(user);
		return user;
	}

	private User createUser() {
		return User.builder()
				.id(1L)
				.first_name("first_name")
				.last_name("last_name")
				.email("email")
				.nickname("nickname")
				.phone(new Phone())
				.build();
	}

}
