package com.locality.usermicroservice.entity;

import org.hibernate.validator.constraints.Length;

import com.locality.usermicroservice.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "buildUser")
@Entity
@Table(name = "users")
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "Username cannot be empty")
	@Length(min = 5, max = 20, message = "Username must have between 5 and 20 characters.")
	@Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$", message = "Invalid username")
	@Column(unique = true)
	private String username;

	@Email(message = "Invalid email")
	@NotBlank(message = "Email cnnot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Invalid email")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Password cannot be empty")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password is invalid")
	@Column(unique = true)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;
}
