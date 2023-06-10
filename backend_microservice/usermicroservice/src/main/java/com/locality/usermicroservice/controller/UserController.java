package com.locality.usermicroservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody @Validated(UserDto.Register.class)UserDto userDto) {

		return new ResponseEntity<UserDto>(this.userService.registerUser(userDto), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> loginUser(@RequestBody @Validated(UserDto.Login.class) UserDto user) {

		return ResponseEntity.ok(this.userService.loginUser(user));

	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") String userId){
		return ResponseEntity.ok(this.userService.getUserById(Long.parseLong(userId)));
	}
	
	@GetMapping("/role/{userId}")
	public ResponseEntity<Map<String, Boolean>> isUserAdmin(@PathVariable(name = "userId") String userId){
		boolean isAdmin = this.userService.isUserAdmin(Long.parseLong(userId));
		if(isAdmin) {
			return ResponseEntity.ok(Map.of("isAdmin", true));
		}
		
		return ResponseEntity.ok(Map.of("isAdmin", false));
		
	}
	
}
