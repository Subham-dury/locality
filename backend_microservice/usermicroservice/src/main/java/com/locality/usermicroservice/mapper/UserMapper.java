package com.locality.usermicroservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.locality.usermicroservice.entity.User;
import com.locality.usermicroservice.payload.UserDto;

@Component
public class UserMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public UserDto userToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}
	
	
	public User dtoToUser(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}
	
	
	public UserDto userToDtoWithoutPassswordAndEmail(User user) {
			return UserDto.builder()
					.userId(user.getUserId())
					.username(user.getUsername())
					.build();
	}
	
}
