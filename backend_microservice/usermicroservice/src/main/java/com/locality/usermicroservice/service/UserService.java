package com.locality.usermicroservice.service;

import com.locality.usermicroservice.entity.User;
import com.locality.usermicroservice.payload.UserDto;

public interface UserService {
	
	public UserDto registerUser(UserDto userDto);

	public UserDto loginUser(UserDto userDto);
	
	public User doesUserExistByName(String username);

	public User doesUserExistByEmail(String email);
	
}
