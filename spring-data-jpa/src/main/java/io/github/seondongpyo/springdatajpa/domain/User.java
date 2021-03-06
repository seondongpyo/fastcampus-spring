package io.github.seondongpyo.springdatajpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
public class User {

	@Id @GeneratedValue
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String email;

	private LocalDateTime createdAt;

	private LocalDateTime lastModifiedAt;

	public User(@NonNull String name, @NonNull String email, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}
}
