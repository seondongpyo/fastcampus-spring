package io.github.seondongpyo.springaop.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.seondongpyo.springaop.annotation.Timer;
import io.github.seondongpyo.springaop.dto.UserDto;

@RequestMapping("/api")
@RestController
public class RestApiController {

	@GetMapping("/get/{id}")
	public String get(@PathVariable Long id, @RequestParam String name) {
		return id + " " + name;
	}

	@PostMapping("/post")
	public UserDto post(@RequestBody UserDto userDto) {
		return userDto;
	}

	@Timer
	@DeleteMapping("/delete")
	public void delete() throws InterruptedException {
		Thread.sleep(2000);
	}

}
