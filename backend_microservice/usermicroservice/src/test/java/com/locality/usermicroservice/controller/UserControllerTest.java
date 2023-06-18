package com.locality.usermicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.TokenService;
import com.locality.usermicroservice.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	@Mock
	private UserService userService;
	
	@Mock
	private TokenService tokenService;
	
	@InjectMocks
	private UserController controllerUnderTest;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void givenUserObject_whenRegisterUser_thenReturnUserDto(){
		
		UserDto userDto = UserDto.builder()
							.username("DummyUser")
							.email("dummy@email.com")
							.password("password")
							.build();
		
		UserDto registeredUserDto = UserDto.builder()
									.userId(1L)
									.username(userDto.getUsername())
									.email(userDto.getEmail())
									.password(userDto.getPassword())
									.build();
		
		when(userService.registerUser(userDto)).thenReturn(registeredUserDto);
		
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        when(tokenService.generateToken(registeredUserDto)).thenReturn(token);
					
		ResponseEntity<UserDto> response = controllerUnderTest.registerUser(userDto);
		UserDto actualUserDto = response.getBody();
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(registeredUserDto, actualUserDto);


	}

}
