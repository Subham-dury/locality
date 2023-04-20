package com.locality.backend.controller;

import java.time.LocalDateTime;
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

import com.locality.backend.entity.User;
import com.locality.backend.response.SuccessResponse;
import com.locality.backend.service.UserService;

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
	
	
	@GetMapping("/")
	public ResponseEntity<SuccessResponse> findUser(@RequestBody User user){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("User found successfully")
				.data(Map.of("user", userService.getUser(user)))
				.build());
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse> findUserByUsername(@PathVariable String id){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found user successfully")
				.data(Map.of("user", userService.getUserById(Long.parseLong(id))))
				.build());
	}
	
	@GetMapping("/all")
	public ResponseEntity<SuccessResponse> findAllUser(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all users successfully")
				.data(Map.of("Users", userService.getAllUser()))
				.build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> updateUser(@RequestBody User user, @PathVariable String id){
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("User updated successfully")
				.data(Map.of("user", userService.updateUser(user, Long.parseLong(id))))
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> deleteUser(@PathVariable String id){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("User deleted successfully")
				.data(Map.of("deleted", userService.deleteUser(Long.parseLong(id))))
				.build());
	}

}
