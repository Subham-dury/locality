package com.locality.usermicroservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
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
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private ModelMapper modelMapper;
	@Mock 
	private UserRepository userRepository;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	private UserServiceImpl serviceUnderTest;

	@BeforeEach
	public void setUp(){
		modelMapper = new ModelMapper();
	}
	
//	Correct User input - User registration success
	
	@Test
	public void givenUserObject_whenRegisterUser_thenReturnUserDtoObject() {
		//Mock
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		User user = modelMapper.map(userDto, User.class);
		User newUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();
		UserDto expectedUserDto = modelMapper.map(newUser, UserDto.class);

		//Stubbing
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		when(userMapper.userToDto(newUser)).thenReturn(expectedUserDto);
		when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		when(userRepository.save(user)).thenReturn(newUser);

		//Asserting
		UserDto actualUserDto = serviceUnderTest.registerUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
		verify(userRepository, times(1)).save(user);
		
	}
	
//	Existing username - User registration failed
	
	@Test
	public void givenExistingUsername_whenRegisterUser_throwResourceExistsException() {
		//Mock
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		User user = modelMapper.map(userDto, User.class);
		User existingUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();

		//Stubbing
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		when(userRepository.findByUsername(user.getUsername())).thenReturn(existingUser);
		//Asserting
		assertThrows(ResourceExistsException.class, () -> serviceUnderTest.registerUser(userDto));
	}
	
//	Existing email - User registration failed
	
	@Test
	public void givenExistingEmail_whenRegisterUser_throwResourceExistsException() {
		//Mock
		UserDto userDto = UserDto.builder()
				.username("dummyUser")
				.email("dummy@email.com")
				.password("Dummy@1")
				.build();
		
		User user = modelMapper.map(userDto, User.class);
		User existingUser = User.builder()
				.userId(1L)
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(Role.MEMBER)
				.build();

		//Stubbing
		when(userMapper.dtoToUser(userDto)).thenReturn(user);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(existingUser);

		//Asserting
		assertThrows(ResourceExistsException.class, () -> serviceUnderTest.registerUser(userDto));
	}
	
//	Correct username details - User login success
	
	@Test
	public void givenUserObject_whenLoginUserByUsername_thenReturnEmployeeObject() {
		//Mock
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
		
		UserDto expectedUserDto = modelMapper.map(expectedUser, UserDto.class);

		//Stubbing
		when(userMapper.userToDto(expectedUser)).thenReturn(expectedUserDto);
		when(userRepository.findByUsername("dummyUser")).thenReturn(expectedUser);

		//Asserting
		UserDto actualUserDto = serviceUnderTest.loginUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
	}	
	
//	Correct useremail details - User login success

	@Test
	public void givenUserObject_whenLoginUserByEmail_thenReturnUserDtoObject() {
		//Mock
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
		
		UserDto expectedUserDto = modelMapper.map(expectedUser, UserDto.class);

		//Stubbing
		when(userMapper.userToDto(expectedUser)).thenReturn(expectedUserDto);
		when(userRepository.findByEmail("dummy@example.com")).thenReturn(expectedUser);

		//Asserting
		UserDto actualUserDto = serviceUnderTest.loginUser(userDto);
		assertThat(expectedUserDto).isNotNull();
		assertEquals(expectedUserDto, actualUserDto);
		
	}
	
//	Correct null user details - User login failed
	@Test
	public void givenNullUsernameEmail_whenLoginUser_throwIllegalArgumentException() {

		//Mock
		UserDto userDto = UserDto.builder()
				.password("Dummy@1")
				.build();

		//Asserting
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect username - User login failed
	@Test
	public void givenIncorrectUsername_whenLoginUserByUsername_throwIllegalArgumentException() {

		//Mock
		UserDto userDto = UserDto.builder()
				.username("invalid-username")
				.password("Dummy@1")
				.build();
		//Stubbing
		when(userRepository.findByUsername(userDto.getUsername())).thenReturn(null);
		//Asserting
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect email - User login failed
	@Test
	public void givenIncorrectEmail_whenLoginUserByEmail_throwIllegalArgumentException() {
		//Mock
		UserDto userDto = UserDto.builder()
				.email("invalid-email")
				.password("Dummy@1")
				.build();
		//Stubbing
		when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
		//Asserting
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
//	Correct incorrect password - User login failed

	@Test
	public void givenIncorrectPassword_whenLoginUser_throwIllegalArgumentException() {
		//Mock
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
			
		//Stubbing
		when(userRepository.findByEmail(userDto.getEmail())).thenReturn(expectedUser);
		//Asserting
		assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.loginUser(userDto));
		
	}
	
}
