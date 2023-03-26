package com.locality.backend.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "buildLocality")
@Entity
@Table(name = "localities")
public class Locality {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long localityId;
	
	@NotNull
	@Length(min = 5, max = 15, message = "Name must have between 5 and 10 characters")
	private String name;
	
	@NotNull(message = "City cannot be null")
	private String city;
	
	@NotNull(message = "State cannot be null")
	private String state;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 4)
	private int img;
	
	@NotNull
	@Length(min = 10, max = 255, message = "about must be precise.")
	private String about;

}
