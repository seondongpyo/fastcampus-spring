package io.github.seondongpyo.springdatajpa.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.seondongpyo.springdatajpa.domain.User;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("사용자 저장")
	@Test
	void save() {
		// given
		User user = new User("홍길동", "hong@gmail.com");
		userRepository.save(user);

		// when
		User foundUser = userRepository.findById(user.getId()).orElse(null);

		// then
		assertThat(foundUser).isNotNull();
		assertThat(foundUser.getName()).isEqualTo(user.getName());
		assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
	}

}