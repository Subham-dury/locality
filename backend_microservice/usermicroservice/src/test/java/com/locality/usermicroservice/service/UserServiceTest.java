package com.locality.usermicroservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.locality.usermicroservice.entity.User;
import com.locality.usermicroservice.enums.Role;
import com.locality.usermicroservice.exception.ResourceExistsException;
import com.locality.usermicroservice.mapper.UserMapper;
import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.repository.UserRepository;
import com.locality.usermicroservice.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock 
	private UserRepository userRepository;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	private UserServiceImpl serviceUnderTest;
	
	
//	Correct User input - User registration success
	
	@Test
	public void givenUserObject_whenRegisterUser_thenReturnUserDtoObject() {
		
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		User user = User.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		
		User newUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();
		
		UserDto expectedUserDto = UserDto.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();
		
		when(userMapper.userToDto(newUser)).thenReturn(expectedUserDto);
		
		when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		when(userRepository.save(user)).thenReturn(newUser);
		
		UserDto actualUserDto = serviceUnderTest.registerUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
		verify(userRepository, times(1)).save(user);
		
	}
	
//	Existing username - User registration failed
	
	@Test
	public void givenExistingUsername_whenRegisterUser_throwResourceExistsException() {
		
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		User user = User.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		
		User existingUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();
		
		when(userRepository.findByUsername(user.getUsername())).thenReturn(existingUser);
		
		assertThrows(ResourceExistsException.class, () -> serviceUnderTest.registerUser(userDto));
	}
	
//	Existing email - User registration failed
	
	@Test
	public void givenExistingEmail_whenRegisterUser_throwResourceExistsException() {
		
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		User user = User.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		
		User existingUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(existingUser);
		
		assertThrows(ResourceExistsException.class, () -> serviceUnderTest.registerUser(userDto));
	}
	
//	Correct username details - User login success
	
	@Test
	public void givenUserObject_whenLoginUserByUsername_thenReturnEmployeeObject() {
		
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.password("Dummy@1")
				.build();

		
		User expectedUser = User.builder()
				.userId(1L)
				.username(userDto.getUsername())
				.email("dummy@email.com")
				.password(userDto.getPassword())
				.role(Role.MEMBER)
				.build();
		
		UserDto expectedUserDto = UserDto.builder()
				.userId(1L)
				.username(userDto.getUsername())
				.email("dummy@email.com")
				.password(userDto.getPassword())
				.role(Role.MEMBER)
				.build();
		
		when(userMapper.userToDto(expectedUser)).thenReturn(expectedUserDto);
		
		when(userRepository.findByUsername("dummyUser")).thenReturn(expectedUser);
		
		UserDto actualUserDto = serviceUnderTest.loginUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
	}	
	
//	Correct useremail details - User login success

	
	@Test
	public void givenUserObject_whenLoginUserByEmail_thenReturnUserDtoObject() {
		
		UserDto userDto = UserDto.builder()
				.email("dummy@example.com")
				.password("Dummy@1")
				.build();

		
		User expectedUser = User.builder()
				.userId(1L)
				.username("dummyUser")
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.role(Role.MEMBER)
				.build();
		
		UserDto expectedUserDto = UserDto.builder()
				.userId(1L)
				.username("dummyUser")
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.role(Role.MEMBER)
				.build();
		
		when(userMapper.userToDto(expectedUser)).thenReturn(expectedUserDto);
		
		when(userRepository.findByEmail("dummy@example.com")).thenReturn(expectedUser);
		
		UserDto actualUserDto = serviceUnderTest.loginUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
	}
	
//	Correct null user details - User login failed

	
	@Test
	public void givenNullUsernameEmail_whenLoginUser_throwIllegalArgumentException() {
		
		UserDto userDto = UserDto.builder()
				.password("Dummy@1")
				.build();
		
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect username - User login failed

	
	@Test
	public void givenIncorrectUsername_whenLoginUserByUsername_throwIllegalArgumentException() {
		
		UserDto userDto = UserDto.builder()
				.username("invalid-username")
				.password("Dummy@1")
				.build();
		
		when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect email - User login failed

	
	@Test
	public void givenIncorrectEmail_whenLoginUserByEmail_throwIllegalArgumentException() {
		
		UserDto userDto = UserDto.builder()
				.email("invalid-email")
				.password("Dummy@1")
				.build();
		
		when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect password - User login failed

	
	@Test
	public void givenIncorrectPassword_whenLoginUser_throwIllegalArgumentException() {
		
		UserDto userDto = UserDto.builder()
				.email("dummy@example.com")
				.password("wrong-password")
				.build();
		
		User expectedUser = User.builder()
				.userId(1L)
				.username("dummyUser")
				.email(userDto.getEmail())
				.password("Dummy@1")
				.role(Role.MEMBER)
				.build();
			
		
		when(userRepository.findByEmail(userDto.getEmail())).thenReturn(expectedUser);
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
}
