package com.locality.backend.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank(message="Locality name cannot be empty")
	@Length(min = 5, max = 15, message = "Name must have between 5 and 10 characters")
	private String name;
	
	@NotBlank(message = "City cannot be empty")
	private String city;
	
	@NotBlank(message = "State cannot be empty")
	private String state;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 4)
	private int img;
	
	@NotBlank(message="Locality description cannot be empty")
	@Length(min = 10, max = 255, message = "About must be precise.")
	private String about;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locality", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> review;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locality", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Event> event;

}
