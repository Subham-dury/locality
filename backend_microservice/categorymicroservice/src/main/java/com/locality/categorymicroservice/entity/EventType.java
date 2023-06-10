package com.locality.categorymicroservice.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "buildEventType")
@Entity
@Table(name = "typeofevents")
public class EventType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventTypeId;
	
	@NotBlank(message="Type cannot be empty")
	@Length(min = 5, max = 50, message = "Event type must be precise.")
	private String typeOfEvent;
	
}
