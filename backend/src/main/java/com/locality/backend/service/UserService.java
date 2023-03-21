package com.locality.backend.service;

import com.locality.backend.entity.User;

public interface UserService {

	public User saveUser(User user) ;
	
	public User updateUser(User user);
	
	public Boolean deleteUser(User user);
	
	public User doesUserExistByName(String username);
	
	public User doesUserExistByEmail(String email);
	
	
	
}
