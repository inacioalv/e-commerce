package br.com.inacioalves.mc.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inacioalves.mc.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByNickname(String nickname);

}
