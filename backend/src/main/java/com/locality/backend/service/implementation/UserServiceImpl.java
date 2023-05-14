package com.locality.backend.service.implementation;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Role;
import com.locality.backend.entity.User;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
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
	public User saveUser(User user) throws DataExistsException{
		
		if(this.userRepository.findAll().isEmpty()) {
			this.userRepository.resetAutoIncrement();
		}
		
		if (user.getPassword().contains(" ")) {
			throw new IllegalArgumentException("Password cannot have space");
		}

		boolean doesUserNameExist = this.doesUserExistByName(user.getUsername()) != null;
		boolean doesUserEmailExist = this.doesUserExistByEmail(user.getEmail()) != null;

		if (doesUserNameExist) {
			log.error("User with above username of ", user.getUsername(), " exists");
			throw new DataExistsException("Username already in use");

		} else if (doesUserEmailExist) {
			log.error("User with above email of " + user.getEmail() + " exists");
			throw new DataExistsException("Email needs to be unique");
		} else {

			log.info("Saving new user with username and email : ", user.getUsername()
					, user.getEmail());
			
			Role role = this.roleRepository.findByRolename("user");
			
			user.setRole(role);
			
			return this.userRepository.save(user);
		}

	}

	@Override
	public User getUser(User user) throws DataNotFoundException{

		User searchedUser = user.getUsername() != null ?
				this.userRepository.findByUsername(user.getUsername())
				: this.userRepository.findByEmail(user.getEmail());

		if (searchedUser == null) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
		} 
		else if (!searchedUser.getPassword().equals(user.getPassword())) {
			log.error("Incorrect password");
			throw new IllegalArgumentException("Incorrect password");
		}
		
		return searchedUser;

	}

	@Override
	public User getUserById(Long id) throws DataNotFoundException{

		Optional<User> searchedUser = userRepository.findById(id);

		if (searchedUser.isEmpty()) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
		}
		
		return searchedUser.get();
	}
	
	@Override
	public User updateUser(User user, Long id) throws DataNotFoundException{
		
		Optional<User> doesUserExist = this.userRepository.findById(id);

		if (doesUserExist.isEmpty()) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
		}

		User updatedUser = doesUserExist.get();
		
		if(updatedUser != null && (updatedUser.getUsername().startsWith(" ") 
				|| updatedUser.getUsername().endsWith(" ")))
			throw new IllegalArgumentException("Username cannot start or end with space");
			
		updatedUser.setUsername(user.getUsername() == null ?
				updatedUser.getUsername() : user.getUsername());
		
		if(updatedUser != null && updatedUser.getEmail().contains(" "))
			throw new IllegalArgumentException("Email cannot contain space");

		updatedUser.setEmail(user.getEmail() == null ? updatedUser.getEmail() : user.getEmail());

		if (user.getPassword() != null && user.getPassword().contains(" "))
			throw new IllegalArgumentException("Password cannot contain space");
		
		updatedUser.setPassword(user.getPassword() == null ?
				updatedUser.getPassword() : user.getPassword());
		
		return this.userRepository.save(updatedUser);
	}

	@Override
	public Boolean deleteUser(Long id) throws DataNotFoundException{

		Optional<User> doesUserExist = this.userRepository.findById(id);

		if (doesUserExist.isEmpty()) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
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