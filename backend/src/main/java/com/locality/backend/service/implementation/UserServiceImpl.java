package com.locality.backend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Role;
import com.locality.backend.entity.User;
import com.locality.backend.exception.EntityAlreadyExistException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.repository.RoleRepository;
import com.locality.backend.repository.UserRepository;
import com.locality.backend.service.UserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;



@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User saveUser(User user) {

		boolean doesUserNameExist = this.doesUserExistByName(user.getUsername()) != null;
		boolean doesUserEmailExist = this.doesUserExistByEmail(user.getEmail()) != null;

		if(doesUserNameExist) {
			log.info("User with above username of ", user.getUsername(), " exists");
			throw new EntityAlreadyExistException("Username needs to be unique");

		} else if(doesUserEmailExist) {
			log.info("User with above email of " + user.getEmail() + " exists");
			throw new EntityAlreadyExistException("Email needs to be unique");
		} else {

			log.info("Saving new user with username and email : ", user.getUsername(), user.getEmail());
			Role role = this.roleRepository.findByName("user");
			user.setRole(role);
			return (this.userRepository.save(user));
		}

	}
	
	@Override
	public User updateUser(User user) {
		User updatedUser = this.userRepository.findByUsername(user.getUsername());
		
		if(updatedUser == null) {
			log.info("user doesn't exist");
			throw new ResourceNotFoundException("User doesn't exist");
		}
		
		updatedUser.setEmail(user.getEmail());
		updatedUser.setPassword(user.getPassword());
		return (this.userRepository.save(updatedUser));
	}
	
	@Override
	public Boolean deleteUser(User user) {
		
		boolean doesUserNotExist = this.doesUserExistByName(user.getUsername()) == null;
		if(doesUserNotExist) {
			log.info("User does't exist");
			throw new ResourceNotFoundException("User does not exist");
		}
		
		this.userRepository.delete(user);
		return true;
	}
	

	@Override
	public User doesUserExistByName(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public User doesUserExistByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

}
