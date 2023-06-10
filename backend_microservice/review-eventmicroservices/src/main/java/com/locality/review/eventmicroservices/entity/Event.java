package com.locality.review.eventmicroservices.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@NotBlank(message = "Event details cannot be empty")
	@Length(min = 10, max = 255, message = "Review must be precise.")
	private String content;

	private Long userId;

	private String username;

	private Long localityId;

	private String localityname;

	private Long eventTypeId;

	private String eventType;

}
