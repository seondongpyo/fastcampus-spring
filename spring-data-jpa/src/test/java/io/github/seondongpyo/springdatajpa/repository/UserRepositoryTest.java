package io.github.seondongpyo.springdatajpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.seondongpyo.springdatajpa.domain.User;

@SpringBootTest
@DisplayName("UserRepository 테스트 - data.sql")
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

	@DisplayName("여러 사용자 저장")
	@Test
	void saveAll() {
		// given
		User user1 = new User("user1", "user1@gmail.com");
		User user2 = new User("user2", "user2@gmail.com");
		userRepository.saveAll(Arrays.asList(user1, user2));

		// when
		List<User> users = userRepository.findAll();

		// then
		assertThat(users).hasSize(7);
	}

	@DisplayName("모든 사용자 찾기")
	@Test
	void findAll() {
		// given
		List<User> users = userRepository.findAll();

		// when
		List<String> userEmails = users.stream().map(User::getEmail).collect(Collectors.toList());

		// then
		assertThat(userEmails).contains(
			"kim@gmail.com",
			"lee@gmail.com",
			"park@gmail.com",
			"hong@gmail.com",
			"choi@gmail.com"
		);
	}

	@DisplayName("특정 아이디에 해당하는 모든 사용자 찾기")
	@Test
	void findAllById() {
		// given
		List<User> users = userRepository.findAllById(Arrays.asList(1L, 3L, 5L));

		// when
		List<String> userNames = users.stream().map(User::getName).collect(Collectors.toList());

		// then
		assertThat(userNames).contains("Kim", "Park", "Choi");
	}

}