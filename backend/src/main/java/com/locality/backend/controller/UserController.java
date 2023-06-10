package com.locality.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.payload.UserDto;
import com.locality.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {

		return new ResponseEntity<UserDto>(this.userService.saveUser(userDto), HttpStatus.CREATED);

	}

	@GetMapping("/")
	public ResponseEntity<UserDto> getUser(@RequestBody UserDto user) {

		return ResponseEntity.ok(this.userService.getUser(user));

	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user,
			@PathVariable String userId) {

		return ResponseEntity.ok(this.userService.updateUser(user, Long.parseLong(userId)));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId) {

		Boolean deleteUser = this.userService.deleteUser(Long.parseLong(userId));
		if (deleteUser) {
			return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
}