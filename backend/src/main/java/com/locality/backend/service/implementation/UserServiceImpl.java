package com.locality.backend.service.implementation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Role;
import com.locality.backend.entity.User;
import com.locality.backend.exception.EntityAlreadyExistException;
import com.locality.backend.exception.EntityNotFoundException;
import com.locality.backend.repository.RoleRepository;
import com.locality.backend.repository.UserRepository;
import com.locality.backend.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

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
		
		if(user.getPassword().contains(" ")) {
			throw new IllegalArgumentException("password cannot have space");
		}

		boolean doesUserNameExist = this.doesUserExistByName(user.getUsername()) != null;
		boolean doesUserEmailExist = this.doesUserExistByEmail(user.getEmail()) != null;

		if (doesUserNameExist) {
			log.info("User with above username of ", user.getUsername(), " exists");
			throw new EntityAlreadyExistException("Username needs to be unique");

		} else if (doesUserEmailExist) {
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
	public User getUser(User user) {
		
		User searchedUser = this.doesUserExistByName(user.getUsername());
		
		if(searchedUser == null) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		else if(!searchedUser.getPassword().equals(user.getPassword())) {
			log.info("Incorrect password");
			throw new EntityNotFoundException("Incorrect password");
		}
		return searchedUser;
 
	}
	
	@Override
	public User getUserById(Long id) {
		
		Optional<User> searchedUser = userRepository.findById(id);
		
		if(searchedUser.isEmpty()) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		
		return searchedUser.get();
	}

	@Override
	public List<User> getAllUser() {
		
		List<User> allUsers= this.userRepository.findAll();
		
		System.out.println("Printing all users");
		
		allUsers.forEach(user -> System.out.println(user.getUsername()));
		
		if(allUsers.isEmpty()) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		
		return allUsers;
	}
	
	@Override
	public User updateUser(User user, Long id) {
		Optional<User> doesUserExist = this.userRepository.findById(id);

		if (doesUserExist.isEmpty()) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}

		User updatedUser = doesUserExist.get();
		
		if(user.getUsername() != null && (user.getUsername().startsWith(" ") || user.getUsername().endsWith(" ")))
			throw new IllegalArgumentException("Username cannot start or end with space");
		updatedUser.setUsername(user.getUsername() == null ? updatedUser.getUsername() : user.getUsername());

		updatedUser.setEmail(user.getEmail() == null ? updatedUser.getEmail() : user.getEmail());
		
		if(user.getPassword() != null && user.getPassword().contains(" "))
			throw new IllegalArgumentException("Password cannot contain space");
		updatedUser.setPassword(user.getPassword() == null ? updatedUser.getPassword() : user.getPassword());
		
		return (this.userRepository.save(updatedUser));
	}
	
	@Override
	public Boolean deleteUser(Long id) {

		Optional<User> doesUserExist = this.userRepository.findById(id);

		if (doesUserExist.isEmpty()) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}

		this.userRepository.deleteById(id);
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
