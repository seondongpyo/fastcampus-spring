package io.github.seondongpyo.springdatajpa.repository;

import io.github.seondongpyo.springdatajpa.domain.User;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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

	@DisplayName("count 구하기")
	@Test
	void count() {
		// given
		// when
		long count = userRepository.count();

		// then
		assertThat(count).isEqualTo(5);
	}

	@DisplayName("existsById 사용하기")
	@Test
	void exists() {
		// given
		// when
		boolean exists1L = userRepository.existsById(1L);
		boolean exists100L = userRepository.existsById(100L);

		// then
		assertThat(exists1L).isTrue();
		assertThat(exists100L).isFalse();
	}

	@DisplayName("사용자 삭제하기")
	@Test
	void delete() {
		// given
		User newUser = userRepository.save(new User());
		User foundUser = userRepository.findById(newUser.getId())
										.orElseThrow(IllegalArgumentException::new);

		// when
		userRepository.delete(foundUser);
		User user = userRepository.findById(newUser.getId()).orElse(null);

		// then
		assertThat(user).isNull();
	}

	@Transactional
	@DisplayName("특정 사용자들 삭제하기")
	@Test
	void deleteAll() {
		// given
		List<User> users = userRepository.findAllById(Arrays.asList(1L, 2L, 3L));

		// when
		userRepository.deleteAll(users);
		List<User> leftUsers = userRepository.findAll();

		// then
		assertThat(leftUsers).hasSize(2);
	}

	@DisplayName("이름으로 사용자 조회")
	@Test
	void findByUsername() {
		// given
		String username = "Kim";

		// when
		Optional<User> foundUser = userRepository.findByName(username);

		// then
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getName()).isEqualTo(username);
	}

	@DisplayName("쿼리 메서드 - 이메일로 User 조회")
	@Test
	void byEmail() {
		// given
		String email = "kim@gmail.com";

		// when
		User foundUser1 = userRepository.findByEmail(email);
		User foundUser2 = userRepository.getByEmail(email);
		User foundUser3 = userRepository.readByEmail(email);
		User foundUser4 = userRepository.searchByEmail(email);
		User foundUser5 = userRepository.streamByEmail(email);
		User foundUser6 = userRepository.findUserByEmail(email);
		User foundUser7 = userRepository.queryByEmail(email);

		// then
		assertThat(foundUser1)
			.isEqualTo(foundUser2)
			.isEqualTo(foundUser3)
			.isEqualTo(foundUser4)
			.isEqualTo(foundUser5)
			.isEqualTo(foundUser6)
			.isEqualTo(foundUser7);
	}

	@Transactional
	@DisplayName("쿼리 메서드 - 첫 번째 User만 조회하기")
	@Test
	void getFirstUser() {
		// given
		String username1 = "Hong";
		String email1 = "hong@naver.com";
		String username2 = "Choi";
		String email2 = "choi@naver.com";

		userRepository.save(new User(username1, email1));
		userRepository.save(new User(username2, email2));

		// when
		User foundUser1 = userRepository.findTop1ByName(username1);
		User foundUser2 = userRepository.findFirst1ByName(username2);

		// then
		assertThat(foundUser1.getName()).isEqualTo(username1);
		assertThat(foundUser1.getEmail()).isNotEqualTo(email1);
		assertThat(foundUser2.getName()).isEqualTo(username2);
		assertThat(foundUser2.getEmail()).isNotEqualTo(email2);
	}

	@Transactional
	@DisplayName("A and B")
	@Test
	void findByNameAndEmail() {
		// given
		User user1 = new User("user", "user1@gmail.com");
		User user2 = new User("user", "user2@gmail.com");
		userRepository.save(user1);
		userRepository.save(user2);

		// when
		User foundUser1 = userRepository.findByNameAndEmail("user", "user1@gmail.com");
		User foundUser2 = userRepository.findByNameAndEmail("user1", "user1@gmail.com");

		// then
		assertThat(foundUser1).isEqualTo(user1);
		assertThat(foundUser2).isNull();
	}

	@Transactional
	@DisplayName("A or B")
	@Test
	void findByNameOrEmail() {
		// given
		User user1 = new User("user1", "user1@gmail.com");
		User user2 = new User("user2", "user2@gmail.com");
		userRepository.save(user1);
		userRepository.save(user2);

		// when
		List<User> users = userRepository.findByNameOrEmail("user1", "user2@gmail.com");

		// then
		assertThat(users).hasSize(2).contains(user1, user2);
	}

	@Transactional
	@DisplayName("AFTER")
	@Test
	void after() {
		// given
		userRepository.save(new User("user1", "user@gmail.com"));

		LocalDateTime now = LocalDateTime.now();
		userRepository.save(new User("user2", "user2@gmail.com", LocalDateTime.now(), LocalDateTime.now()));
		userRepository.save(new User("user3", "user3@gmail.com", LocalDateTime.now(), LocalDateTime.now()));

		// when
		List<User> users = userRepository.findAllByCreatedAtAfter(now);

		// then
		assertThat(users).hasSize(2);
	}

	@Transactional
	@DisplayName("BEFORE")
	@Test
	void before() {
		// given
		LocalDateTime now = LocalDateTime.now();

		// when
		List<User> users = userRepository.findAllByCreatedAtBefore(now);

		// then
		assertThat(users).hasSize(5);
	}

	@Transactional
	@DisplayName("NOT NULL")
	@Test
	void notNull() {
		// given
		userRepository.save(new User());
		userRepository.save(new User());

		// when
		List<User> users = userRepository.findAllByNameIsNotNull();

		// then
		assertThat(users).hasSize(5);
		assertThat(users.size()).isNotEqualTo(7);
	}

	@DisplayName("IN")
	@Test
	void in() {
		// given
		List<String> names = Arrays.asList("Kim", "Hong", "Choi");

		// when
		List<User> users = userRepository.findAllByNameIn(names);
		List<String> userNames = users.stream().map(User::getName).collect(Collectors.toList());

		// then
		assertThat(users).hasSize(3);
		assertThat(userNames).containsExactly("Kim", "Hong", "Choi");
	}

	@Transactional
	@DisplayName("START WITH")
	@Test
	void startWith() {
		// given
		userRepository.save(new User("Kang", "kang@gmail.com"));

		// when
		List<User> users = userRepository.findAllByEmailStartingWith("k");

		// then
		assertThat(users).hasSize(2);
	}

	@Transactional
	@DisplayName("END WITH")
	@Test
	void endWith() {
		// given
		userRepository.save(new User("a", "a@hanmail.net"));
		userRepository.save(new User("b", "b@hanmail.net"));
		userRepository.save(new User("c", "c@nate.com"));
		userRepository.save(new User("d", "b@naver.com"));

		// when
		List<User> users = userRepository.findAllByEmailEndingWith(".net");

		// then
		assertThat(users).hasSize(2);
	}

	@Transactional
	@DisplayName("CONTAINS")
	@Test
	void contains() {
		// given
		userRepository.save(new User("Kang", "kang@yahoo.com"));
		userRepository.save(new User("Noh", "noh@gmail.com"));
		userRepository.save(new User("Hwang", "hwang@lycos.co.kr"));
		userRepository.save(new User("Jang", "jang@nate.com"));
		userRepository.save(new User("Jung", "jung@hanmail.net"));

		// when
		List<User> users = userRepository.findAllByNameContains("an");

		// then
		assertThat(users).hasSize(3);
	}

	@Transactional
	@DisplayName("LIKE")
	@Test
	void like() {
		// given
		userRepository.save(new User("Kang", "kang@yahoo.com"));
		userRepository.save(new User("Noh", "noh@gmail.com"));
		userRepository.save(new User("Hwang", "hwang@lycos.co.kr"));
		userRepository.save(new User("Jang", "jang@nate.com"));
		userRepository.save(new User("Jung", "jung@hanmail.net"));

		// when
		List<User> users1 = userRepository.findAllByNameLike("%an%");
		List<User> users2 = userRepository.findAllByNameLike("an");

		// then
		assertThat(users1).hasSize(3);
		assertThat(users2).isEmpty();
	}

}