package com.locality.backend.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor(staticName = "buildEvent")
@Entity
@Table(name = "events")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;
	
	@Column(nullable = false)
	private LocalDate postDate;
	
	@Column(nullable = false)
	private LocalDate eventDate;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 4)
	private int img;

	@NotBlank(message="Event details cannot be empty")
	@Length(min = 10, max = 255, message = "Review must be precise.")
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "locality_id")
	private Locality locality;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="eventType_id")
	private EventType eventType;

}
