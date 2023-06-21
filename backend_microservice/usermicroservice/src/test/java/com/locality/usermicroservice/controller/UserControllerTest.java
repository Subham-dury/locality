package com.locality.usermicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.locality.usermicroservice.exception.ExpiredTokenException;
import com.locality.usermicroservice.payload.UserAuthDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.TokenService;
import com.locality.usermicroservice.service.UserService;

import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserController controllerUnderTest;

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

    @Test
    public void givenUserObject_whenRegisterUser_thenReturnUserDto() {
        //Mock
        UserDto userDto = UserDto.builder()
                .username("DummyUser")
                .email("dummy@email.com")
                .password("password")
                .build();

        UserDto registeredUserDto = UserDto.builder()
                .userId(1L)
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        //Stabbing
        when(userService.registerUser(userDto)).thenReturn(registeredUserDto);
        when(tokenService.generateToken(registeredUserDto)).thenReturn(token);

        //Asserting
        ResponseEntity<UserDto> response = controllerUnderTest.registerUser(userDto);
        UserDto actualUserDto = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(registeredUserDto, actualUserDto);
    }

    @Test
    public void givenInvalidUserObject_whenRegisterUser_thenFailValidation() {
        //Mock
        UserDto userDto = UserDto.builder().build();

        Set<ConstraintViolation<UserDto>> errors = validator.validate(userDto, UserDto.Register.class);
        assertThat(errors).hasSize(2);

    }

    @Test
    public void givenUserObject_whenLoginUser_thenReturnUserDto() {
        //Mock
        UserDto userDto = UserDto.builder()
                .email("dummy@email.com")
                .password("password")
                .build();

        UserDto loginUserDto = UserDto.builder()
                .userId(1L)
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        //Stabbing
        when(userService.loginUser(userDto)).thenReturn(loginUserDto);
        when(tokenService.generateToken(loginUserDto)).thenReturn(token);

        //Asserting
        ResponseEntity<UserDto> response = controllerUnderTest.loginUser(userDto);
        UserDto actualUserDto = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginUserDto, actualUserDto);

    }

    @Test
    public void givenInvalidUserObject_whenLoginUser_thenFailValidation() {
        //Mock
        UserDto userDto = UserDto.builder().build();

        Set<ConstraintViolation<UserDto>> errors = validator.validate(userDto, UserDto.Register.class);
        assertThat(errors).hasSize(2);
    }

    @Test
    public void givenValidToken_whenAuthorizeUser_thenReturnAuthorizedUser() {
        //Mock

        UserAuthDto validatedUser = UserAuthDto.builder().build();

        //Stubbing
        when(tokenService.validateUser(token.substring(7))).thenReturn(validatedUser);

        //Asserting
        ResponseEntity<UserAuthDto> response = controllerUnderTest.authorizeUser(token);
        assertThat(response.getBody()).isNotNull();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void givenInvalidToken_whenAuthorizeUser_thenThrowException() {

        //Stubbing
        when(tokenService.validateUser(token.substring(7))).thenThrow(ExpiredTokenException.class);

        //Asserting
        assertThrows(ExpiredTokenException.class, () -> controllerUnderTest.authorizeUser(token));

    }

}
