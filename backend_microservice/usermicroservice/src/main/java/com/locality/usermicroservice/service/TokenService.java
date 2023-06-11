package com.locality.usermicroservice.service;

import java.util.Date;

import com.locality.usermicroservice.payload.UserAuthDto;
import com.locality.usermicroservice.payload.UserDto;

public interface TokenService {
	
	public String generateToken(UserDto user);

	public Boolean validateToken(String token);
	
	public Boolean isTokenExpired(Date expirationDate);
	
	public UserAuthDto validateUser(String token);

}
