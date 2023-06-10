package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Review;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.ReviewDto;

public interface ReviewService {

	public ReviewDto saveReview(ReviewDto reviewDto, Long userId, Long localityId)
			throws ResourceNotFoundException, ResourceExistsException;

	public List<ReviewDto> getAllReview() throws ResourceNotFoundException;

	public List<ReviewDto> getRecentReview() throws ResourceNotFoundException;;

	public List<ReviewDto> getAllReviewByLocality(Long localityId)
			throws ResourceNotFoundException, IllegalArgumentException;

	public List<ReviewDto> getAllReviewByUser(Long userId)
			throws ResourceNotFoundException, IllegalArgumentException;

	public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId)
			throws ResourceNotFoundException, IllegalArgumentException;

	public boolean deleteReview(Long reviewId) throws ResourceNotFoundException;;

	public Review dtoToReview(ReviewDto reviewDto);

	public ReviewDto reviewToDto(Review review);
}
