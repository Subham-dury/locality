package com.locality.review.eventmicroservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.payload.ReviewDto;

@Component
public class ReviewMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	

	public Review dtoToReview(ReviewDto reviewDto) {
		return this.modelMapper.map(reviewDto, Review.class);
	}

	public ReviewDto reviewToDto(Review review) {
		
		return ReviewDto.buildReviewDto(review.getReviewId(), 
				review.getDate(),
				review.getImg(),
				review.getContent(),
				null,
				review.getUsername(),
				null,
				review.getLocalityname());
		
	}

}
