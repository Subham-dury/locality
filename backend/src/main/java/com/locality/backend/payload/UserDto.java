package com.locality.backend.payload;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.locality.backend.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "userDtoBuilder")
public class UserDto {
	
	private Long userId;
	
	@NotBlank(message="Username cannot be empty")
    @Length(min = 5, max = 20, message = "Username must have between 5 and 20 characters.")
	private String username;
	
	@Email(message = "Invalid email")
	@NotBlank(message = "Email cannot be empty")
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private Role role;
	
}
