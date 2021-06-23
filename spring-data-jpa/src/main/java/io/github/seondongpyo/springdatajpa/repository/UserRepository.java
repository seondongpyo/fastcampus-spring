package io.github.seondongpyo.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.seondongpyo.springdatajpa.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// Query Method 추가
	User findByName(String username);

}
