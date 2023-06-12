package com.locality.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.usermicroservice.payload.UserAuthDto;
import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.TokenService;
import com.locality.usermicroservice.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody @Validated(UserDto.Register.class) UserDto userDto,
			HttpServletResponse response) {
		
		UserDto registerUser = this.userService.registerUser(userDto);
		String token = this.tokenService.generateToken(registerUser);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
	                .header("Authorization", "Bearer:" + token)
	                .body(registerUser);

	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> loginUser(@RequestBody @Validated(UserDto.Login.class) UserDto user,
			HttpServletResponse response) {

		UserDto loginUser = this.userService.loginUser(user);
		String token = this.tokenService.generateToken(loginUser);

		return ResponseEntity.status(HttpStatus.OK)
				.header("Authorization", "Bearer:" + token)
				.body(loginUser);

	}

	
	@GetMapping("/authorize")
	public ResponseEntity<UserAuthDto> authorizeUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		
		return ResponseEntity.ok(this.tokenService.validateUser(token.substring(7)));
	}

}
