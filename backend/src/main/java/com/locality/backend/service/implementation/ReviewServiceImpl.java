package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.ReviewDto;
import com.locality.backend.repository.ReviewRepository;
import com.locality.backend.service.LocalityService;
import com.locality.backend.service.ReviewService;
import com.locality.backend.service.UserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private LocalityService localityService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ReviewDto saveReview(ReviewDto reviewDto, Long userId, Long localityId) 
			throws ResourceNotFoundException, ResourceExistsException{

		if (this.reviewRepository.findAll().isEmpty()) {
			this.reviewRepository.resetAutoIncrement();
		}

		User user = this.userService.getUserById(userId);

		if (user == null) {
			log.error("User with id "+userId+" not found");
			throw new ResourceNotFoundException("User with id "+userId+" not found");
		}

		Locality locality = this.localityService.getLocalityById(localityId);

		if (locality == null) {
			log.error("Locality with id "+localityId+" not found");
			throw new ResourceNotFoundException("Locality with id "+localityId+" not found");
		}

		if (!this.reviewRepository.findByUserAndLocality(user, locality).isEmpty()) {
			log.warn("Duplicate review");
			throw new ResourceExistsException("You have already added a review for the selected locality");
		}
		
		Review review = this.dtoToReview(reviewDto);

		log.info("Saving new review");
		review.setImg((int) (Math.random() * 4) + 1);
		review.setDate(LocalDate.now());
		review.setUser(user);
		review.setLocality(locality);
		return this.reviewToDto(this.reviewRepository.save(review));
	}

	@Override
	public List<ReviewDto> getAllReview() throws ResourceNotFoundException{

		List<Review> reviews = this.reviewRepository.findAll();

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

		List<Review> reviews = this.reviewRepository.findTop10ByOrderByDateDesc();

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

		Locality locality = this.localityService.getLocalityById(localityId);

		if (locality == null) {
			log.error("Locality not found");
			throw new ResourceNotFoundException("Locality not found");
		}

		List<Review> reviews = reviewRepository.findByLocality(locality);

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

		User user = this.userService.getUserById(userId);

		if (user == null) {
			log.error("User not found");
			throw new IllegalArgumentException("User not found");
		}

		List<Review> reviews = reviewRepository.findByUser(user);

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
				review.getUser().getUsername(),
				review.getLocality().getName());
		
	}
}
