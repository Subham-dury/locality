package com.locality.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.locality.usermicroservice.enums.Role;
import com.locality.usermicroservice.exception.ExpiredTokenException;
import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.impl.TokenServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

	@InjectMocks
	private TokenServiceImpl serviceUnderTest;
	
	@Test
	public void givenValidUser_whenGenerateToken_thenReturnToken() {
		
		UserDto user = UserDto.builder()
				.userId(6L)
				.username("Subham")
				.email("Subham@email.com")
				.role(Role.MEMBER)
				.build();
		
		String token = serviceUnderTest.generateToken(user);
		
		assertNotNull(token);
		assertEquals(3, token.split("\\.").length);
	}
	
	@Test
	public void givenInvalidUser_whenGenerateToken_throwException() {
		
		UserDto user = UserDto.builder()
				.userId(6L)
				.username("Subham")
				.email("Subham@email.com")
				.build();
				
		assertThrows(NullPointerException.class, () -> serviceUnderTest.generateToken(user));
	}

	@Test
	public void givenIncorrectToken_whenValidateToken_throwExpiredTokenException() {

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
		
		assertThrows(ExpiredTokenException.class, () -> serviceUnderTest.validateToken(token));
	}
	
	@Test
	public void givenIncorrectToken_whenValidateUser_throwExpiredTokenException() {
		
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTkxMTg2LCJleHAiOjE2ODcwNzc1ODZ9.0CaCDQq-lu1p5lbzxTluf-zvpG3hOF_9Fd1z-ug_bic";
		
		assertThrows(ExpiredTokenException.class, () -> serviceUnderTest.validateToken(token));
	}

}
