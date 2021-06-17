package io.github.seondongpyo.springdatajpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.github.seondongpyo.springdatajpa.domain.User;

@SpringBootTest
@DisplayName("UserRepository 테스트 - data.sql")
class UserRepositoryTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepository userRepository;

	@Transactional
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

	@Transactional
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
	
	@DisplayName("프록시 조회하기")
	@Test
	void proxy() {
		// given
		User userProxy = userRepository.getOne(1L);

		// when
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();

		// then
		assertThat(userProxy.getClass()).isNotEqualTo(User.class);
		assertThat(persistenceUnitUtil.isLoaded(userProxy)).isFalse();
	}

	@Transactional
	@DisplayName("프록시 초기화하기")
	@Test
	void initProxy() {
		// given
		User userProxy = userRepository.getOne(1L);

		// when
		Hibernate.initialize(userProxy);
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();

		// then
		assertThat(persistenceUnitUtil.isLoaded(userProxy)).isTrue();
	}

}