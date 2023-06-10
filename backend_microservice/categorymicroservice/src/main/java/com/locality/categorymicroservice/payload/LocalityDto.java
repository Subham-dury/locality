package com.locality.categorymicroservice.payload;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "localityDtoBuilder")
@NoArgsConstructor
@Builder
@JsonInclude(content = Include.NON_NULL)
public class LocalityDto {
	
	private Long localityId;
	
	@NotEmpty(message="Locality name cannot be empty")
	@Length(min = 5, max = 15, message = "Name must have between 5 and 10 characters")
	private String name;
	
	@NotEmpty(message = "City cannot be empty")
	private String city;
	
	@NotEmpty(message = "State cannot be empty")
	private String state;
	
	private int img;
	
	@NotEmpty(message="Locality description cannot be empty")
	@Length(min = 10, max = 255, message = "About must be precise.")
	private String about;
	
}
