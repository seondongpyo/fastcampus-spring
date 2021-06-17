package io.github.seondongpyo.springdatajpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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

}
