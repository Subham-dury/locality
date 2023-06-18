package com.locality.usermicroservice.service;

import com.locality.usermicroservice.payload.UserAuthDto;
import com.locality.usermicroservice.payload.UserDto;

public interface TokenService {
	
	public String generateToken(UserDto user);

	public Boolean validateToken(String token);
	
	public UserAuthDto validateUser(String token);

}
