package com.locality.backend.service.implementation;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Role;
import com.locality.backend.entity.User;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.UserDto;
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
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto saveUser(UserDto userDto) throws ResourceExistsException{
		
		if(this.userRepository.findAll().isEmpty()) {
			this.userRepository.resetAutoIncrement();
		}
		
		User user = this.dtoToUser(userDto);
		
		if (user.getPassword().contains(" ")) {
			throw new IllegalArgumentException("Password cannot have space");
		}

		boolean doesUserNameExist = this.doesUserExistByName(user.getUsername()) != null;
		boolean doesUserEmailExist = this.doesUserExistByEmail(user.getEmail()) != null;

		if (doesUserNameExist) {
			log.error("User with above username of "+ user.getUsername()+ " exists");
			throw new ResourceExistsException("User with above username of "+ user.getUsername()+ " exists");

		} else if (doesUserEmailExist) {
			log.error("User with above email of " + user.getEmail() + " exists");
			throw new ResourceExistsException("User with above email of " + user.getEmail() + " exists");
		} else {

			log.info("Saving new user with username and email : ", user.getUsername()
					, user.getEmail());
			
			Role role = this.roleRepository.findByRolename("user");
			
			user.setRole(role);
			
			User savedUser = this.userRepository.save(user);
			System.out.println(savedUser.getPassword());
			return this.userToDto(savedUser);
		}

	}

	@Override
	public UserDto getUser(UserDto user) throws IllegalArgumentException {
		
		if(user.getEmail() == null && user.getUsername() == null) {
			throw new IllegalArgumentException("Both username and email cannot be null");
		}
		
		if(this.doesUserExistByName(user.getUsername()) == null ||
				this.doesUserExistByEmail(user.getEmail()) == null){
			throw new IllegalArgumentException("Username or email is incorrect");
		}
		
		User searchedUser = user.getUsername() == null ?
				this.userRepository.findByEmail(user.getEmail()) :
					this.userRepository.findByUsername(user.getUsername());
		
		if(!searchedUser.getPassword().equals(user.getPassword())){
			throw new IllegalArgumentException("Incorrect password");
		}
		
		return this.userToDto(searchedUser);
		
	}

	@Override
	public User getUserById(Long userId) throws ResourceNotFoundException{

		Optional<User> searchedUser = userRepository.findById(userId);

		if (searchedUser.isEmpty()) {
			log.error("User with id "+userId+" not found");
			throw new ResourceNotFoundException("User with id "+userId+" not found");
		}
		
		return searchedUser.get();
	}
	
	@Override
	public UserDto updateUser(UserDto user, Long userId) throws ResourceNotFoundException,
	IllegalArgumentException{
		
		Optional<User> doesUserExist = this.userRepository.findById(userId);

		if (doesUserExist.isEmpty()) {
			log.error("User with id "+userId+" not found");
			throw new ResourceNotFoundException("User with id "+userId+" not found");
		}

		User toUpdateUser = doesUserExist.get();
		
		
		if(user.getUsername() != null && user.getUsername().trim().isEmpty())
			throw new IllegalArgumentException("Username cannot be blank");
			
		toUpdateUser.setUsername(user.getUsername() == null ?
				toUpdateUser.getUsername() : user.getUsername().trim());
		
		if(user.getEmail() != null && user.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email cannot be blank");
		}

		toUpdateUser.setEmail(user.getEmail() == null ? toUpdateUser.getEmail() : user.getEmail());

		if (user.getPassword() != null && user.getPassword().contains(" "))
			throw new IllegalArgumentException("Password cannot contain space");
		
		toUpdateUser.setPassword(user.getPassword() == null ?
				toUpdateUser.getPassword() : user.getPassword());
		
		User updatedUser =  this.userRepository.save(toUpdateUser);
		return this.userToDto(updatedUser);
	}

	@Override
	public Boolean deleteUser(Long userId) throws ResourceNotFoundException{

		Optional<User> doesUserExist = this.userRepository.findById(userId);

		if (doesUserExist.isEmpty()) {
			log.error("User with id "+userId+" not found");
			throw new ResourceNotFoundException("User with id "+userId+" not found");
		}

		this.userRepository.deleteById(userId);
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
	
	public UserDto userToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}
	
	public User dtoToUser(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}
	
}