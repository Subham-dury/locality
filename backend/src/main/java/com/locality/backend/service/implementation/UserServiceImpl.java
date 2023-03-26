package com.locality.backend.service.implementation;

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
		
		if(searchedUser == null || !searchedUser.getPassword().equals(user.getPassword())) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}

		return searchedUser;
 
	}
	
	@Override
	public User getUserByUsername(String username) {
		
		User searchedUser = this.doesUserExistByName(username);
		
		if(searchedUser == null) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		
		return searchedUser;
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
	public List<User> getUserByRole(String role) {
		
		
		List<User> allUsers = this.userRepository.findByRole(role);
		
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
		updatedUser.setUsername(user.getUsername() == null
				? updatedUser.getUsername() : user.getUsername());
		
		updatedUser.setEmail(user.getEmail() == null
				? updatedUser.getEmail() : user.getEmail());
		
		updatedUser.setPassword(user.getPassword() == null
				? updatedUser.getPassword() : user.getPassword());
		
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
