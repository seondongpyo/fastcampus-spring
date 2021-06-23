package io.github.seondongpyo.springdatajpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.seondongpyo.springdatajpa.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// Query Method 추가
	Optional<User> findByName(String username);

}
