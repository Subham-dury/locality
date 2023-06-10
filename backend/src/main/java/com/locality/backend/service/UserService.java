package com.locality.backend.service;

import com.locality.backend.entity.User;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.UserDto;

public interface UserService {

	public UserDto saveUser(UserDto user) throws ResourceExistsException;

	public UserDto getUser(UserDto user) throws IllegalArgumentException;

	public User getUserById(Long userId) throws ResourceNotFoundException;

	public UserDto updateUser(UserDto user, Long userId) throws ResourceNotFoundException, 
		IllegalArgumentException;

	public Boolean deleteUser(Long userId) throws ResourceNotFoundException;

	public User doesUserExistByName(String username);

	public User doesUserExistByEmail(String email);

	public UserDto userToDto(User user);

	public User dtoToUser(UserDto userDto);

}
