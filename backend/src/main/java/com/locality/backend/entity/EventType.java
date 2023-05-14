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
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eventType", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Event> event;
	
}
