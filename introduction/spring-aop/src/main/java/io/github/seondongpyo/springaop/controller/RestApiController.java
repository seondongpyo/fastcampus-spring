package io.github.seondongpyo.springaop.controller;

import io.github.seondongpyo.springaop.annotation.Decode;
import io.github.seondongpyo.springaop.annotation.Timer;
import io.github.seondongpyo.springaop.dto.UserDto;
import org.springframework.web.bind.annotation.*;

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

	@Decode
	@PutMapping("/put")
	public UserDto put(@RequestBody UserDto userDto) {
		return userDto;
	}

}
