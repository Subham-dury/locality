package com.locality.usermicroservice.payload;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.locality.usermicroservice.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
public class UserDto {

	private Long userId;

	@NotBlank(message = "Username cannot be empty", groups = { Register.class })
	@Length(min = 5, max = 20, message = "Username must have between 5 and 20 characters.",
		groups = { Register.class })
	@Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$", message = "Invalid username", groups = { Register.class })
	private String username;

	@Email(message = "Invalid email", groups = { Register.class })
	@NotBlank(message = "Email cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
		message = "Invalid email", groups = {Register.class })
	private String email;

	@NotBlank(message = "Password cannot be empty", groups = { Login.class, Register.class })
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
		message = "Username contains invalid characters", groups = {Login.class, Register.class })
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@NotNull(message = "Role cannot be null")
	private Role role;

	public interface Login {
	}

	public interface Register {
	}

}
