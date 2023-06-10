package com.locality.review.eventmicroservices.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.repository.ReviewRepository;
import com.locality.review.eventmicroservices.service.FetchService;
import com.locality.review.eventmicroservices.service.ReviewService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private FetchService fetchService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ReviewDto saveReview(ReviewDto reviewDto, Long userId, Long localityId) 
			throws ResourceNotFoundException, ResourceExistsException{
		
		UserDto user = this.fetchService.getUser(userId);
		LocalityAndEventTypeDto locality = this.fetchService.getLocality(localityId);
		

		if (!this.reviewRepository.findByUserIdAndLocalityIdOrderByDate(userId, localityId).isEmpty()) {
			log.warn("Duplicate review");
			throw new ResourceExistsException("You have already added a review for the selected locality");
		}
		
		Review review = this.dtoToReview(reviewDto);

		log.info("Saving new review");
		review.setImg((int) (Math.random() * 4) + 1);
		review.setDate(LocalDate.now());
		review.setUserId(user.getUserId());
		review.setUsername(user.getUsername());
		review.setLocalityId(locality.getLocalityId());
		review.setLocalityname(locality.getName());
		return this.reviewToDto(this.reviewRepository.save(review));
	}

	@Override
	public List<ReviewDto> getAllReview() throws ResourceNotFoundException{

		List<Review> reviews = this.reviewRepository.findByOrderByDate();

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new ResourceNotFoundException("Reviews not found");
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> this.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getRecentReview() throws ResourceNotFoundException{

		List<Review> reviews = this.reviewRepository.findTop10ByOrderByDate();

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new ResourceNotFoundException("Reviews not found");
		}

		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> this.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getAllReviewByLocality(Long localityId) throws ResourceNotFoundException,
			IllegalArgumentException{

		this.fetchService.getLocality(localityId);

		List<Review> reviews = reviewRepository.findByLocalityIdOrderByDate(localityId);

		if (reviews.isEmpty()) {
			log.error("Reviews not found for locality id "+localityId);
			throw new ResourceNotFoundException("Reviews not found for locality id "+localityId);
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> this.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getAllReviewByUser(Long userId) throws ResourceNotFoundException,
		IllegalArgumentException{

		//System.out.println(this.fetchService.getUser(userId));

		List<Review> reviews = reviewRepository.findByUserIdOrderByDate(userId);

		if (reviews.isEmpty()) {
			log.error("Reviews not found for user with id "+userId);
			throw new ResourceNotFoundException("Reviews not found for user with id "+userId);
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> this.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId) {
		
		if(reviewDto == null) {
			throw new IllegalArgumentException("No review body found for update");
		}

		Optional<Review> searchedReview = reviewRepository.findById(reviewId);

		if (searchedReview.isEmpty()) {
			log.error("Review not found for review id "+reviewId);
			throw new ResourceNotFoundException("Review not found for review id "+reviewId);
		}

		Review updatedReview = searchedReview.get();
		
		updatedReview.setContent(reviewDto.getContent() == null ?
				updatedReview.getContent() : reviewDto.getContent());

		return this.reviewToDto(this.reviewRepository.save(updatedReview));
	}

	@Override
	public boolean deleteReview(Long reviewId) throws ResourceNotFoundException{
		Optional<Review> doesReviewExist = reviewRepository.findById(reviewId);

		if (doesReviewExist.isEmpty()) {
			log.error("Review not found for review id "+reviewId);
			throw new ResourceNotFoundException("Review not found for review id "+reviewId);
		}

		reviewRepository.deleteById(reviewId);
		return true;
	}


	@Override
	public Review dtoToReview(ReviewDto reviewDto) {
		return this.modelMapper.map(reviewDto, Review.class);
	}

	@Override
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
