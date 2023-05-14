package com.locality.backend.service;

import com.locality.backend.entity.User;

public interface UserService {

public User saveUser(User user);
	
	public User getUser(User user);
	
	public User getUserById(Long userId);

	public User updateUser(User user, Long id);
	
	public Boolean deleteUser(Long id);
	
	public User doesUserExistByName(String username);
	
	public User doesUserExistByEmail(String email);
	
	
	
}
