package com.locality.backend.payload;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "buildReviewDto")
public class ReviewDto {
	
	private Long reviewId;
	
	private LocalDate date;
	
	private int img;
	
	@NotBlank(message="Review cannot be empty")
	@Length(min = 10, max = 255, message = "Review must have 10 to 255 characters.")
	private String content;
	
	private String username;
	
	private String localityname;
	
}
