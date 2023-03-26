package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.User;

public interface UserService {

	public User saveUser(User user);
	
	public User getUser(User user);
		
	public User getUserByUsername(String username);
	
	public List<User> getAllUser();
	
	public List<User> getUserByRole(String role);
	
	public User updateUser(User user, Long id);
	
	public Boolean deleteUser(Long id);
	
	public User doesUserExistByName(String username);
	
	public User doesUserExistByEmail(String email);
	
	
	
}
