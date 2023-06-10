package com.locality.usermicroservice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.usermicroservice.entity.User;
import com.locality.usermicroservice.enums.Role;
import com.locality.usermicroservice.exception.ResourceExistsException;
import com.locality.usermicroservice.exception.ResourceNotFoundException;
import com.locality.usermicroservice.mapper.UserMapper;
import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.repository.UserRepository;
import com.locality.usermicroservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDto registerUser(UserDto userDto) throws ResourceExistsException {

		User user = userMapper.dtoToUser(userDto);

		boolean doesUserNameExist = this.doesUserExistByName(user.getUsername()) != null;
		boolean doesUserEmailExist = this.doesUserExistByEmail(user.getEmail()) != null;

		if (doesUserNameExist) {
			throw new ResourceExistsException("User with above username of " + user.getUsername() + " exists");

		} else if (doesUserEmailExist) {
			throw new ResourceExistsException("User with above email of " + user.getEmail() + " exists");
		} else {

			log.info("Saving new user with username and email : ", user.getUsername(), user.getEmail());

			user.setRole(Role.MEMBER);

			User savedUser = this.userRepository.save(user);
			return userMapper.userToDto(savedUser);
		}
	}

	@Override
	public UserDto loginUser(UserDto userDto) {

		if (userDto.getEmail() == null && userDto.getUsername() == null) {
			throw new IllegalArgumentException("Both username and email cannot be null");
		}

		if (userDto.getUsername() != null && this.doesUserExistByName(userDto.getUsername()) == null) {
			throw new IllegalArgumentException("Incorrect username");
		}

		if (userDto.getUsername() == null && userDto.getEmail() != null
				&& this.doesUserExistByEmail(userDto.getEmail()) == null) {
			throw new IllegalArgumentException("Incorrect email");
		}

		User searchedUser = userDto.getUsername() == null ? this.userRepository.findByEmail(userDto.getEmail())
				: this.userRepository.findByUsername(userDto.getUsername());

		if (!searchedUser.getPassword().equals(userDto.getPassword())) {
			throw new IllegalArgumentException("Incorrect password");
		}

		return userMapper.userToDto(searchedUser);
	}
	
	
	@Override
	public Boolean isUserAdmin(Long userId)throws ResourceNotFoundException {
		Optional<User> findUserById = this.userRepository.findById(userId);
		
		if(findUserById.isEmpty()) {
			throw new ResourceNotFoundException("User with user id of "+userId+" not found");
		}
		
		return (findUserById.get().getRole() == Role.ADMIN);
	}
	
	@Override
	public UserDto getUserById(Long userId) throws ResourceNotFoundException{
		
		Optional<User> findById = this.userRepository.findById(userId);
		if(findById.isEmpty()) {
			throw new ResourceNotFoundException("User not found");
		}
		
		return userMapper.userToDtoWithoutPassswordAndEmail(findById.get());
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
