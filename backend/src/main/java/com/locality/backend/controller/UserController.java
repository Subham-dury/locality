package com.locality.backend.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.User;
import com.locality.backend.response.ExceptionResponse;
import com.locality.backend.response.SuccessResponse;
import com.locality.backend.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<SuccessResponse> saveUser(@RequestBody User user){
		
		
		return new ResponseEntity<SuccessResponse>(SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED)
				.message("User created successfully")
				.data(Map.of("user", userService.saveUser(user)))
				.build(),
				HttpStatus.CREATED);
			
	}
	
	@PutMapping("/")
	public ResponseEntity<SuccessResponse> updateUser(@RequestBody User user){
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("User updated successfully")
				.data(Map.of("user", userService.updateUser(user)))
				.build());
	}
	
	@DeleteMapping("/")
	public ResponseEntity<SuccessResponse> deleteUser(@RequestBody User user){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("User deleted successfully")
				.data(Map.of("delted", userService.deleteUser(user)))
				.build());
	}

}
