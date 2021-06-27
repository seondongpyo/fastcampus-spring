package io.github.seondongpyo.springdatajpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.seondongpyo.springdatajpa.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// Query Method 추가
	Optional<User> findByName(String username);

	User findByEmail(String email);
	User getByEmail(String email);
	User readByEmail(String email);
	User queryByEmail(String email);
	User searchByEmail(String email);
	User streamByEmail(String email);
	User findUserByEmail(String email);

	User findFirst1ByName(String name);
	User findTop1ByName(String name);

	User findByNameAndEmail(String name, String email);
	List<User> findByNameOrEmail(String name, String email);

	List<User> findAllByCreatedAtAfter(LocalDateTime localDateTime);
	List<User> findAllByCreatedAtBefore(LocalDateTime localDateTime);

	List<User> findAllByNameIsNotNull();
}
