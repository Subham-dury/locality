package com.locality.review.eventmicroservices.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.mapper.ReviewMapper;
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
	private ReviewMapper reviewMapper;
	
	Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "date");
	
	@Override
	public ReviewDto saveReview(ReviewDto reviewDto, String token, Long localityId) 
			throws ResourceNotFoundException, ResourceExistsException{
		
		UserDto user = this.fetchService.validateUser(token);
		LocalityAndEventTypeDto locality = this.fetchService.getLocality(localityId);
		

		if (!this.reviewRepository.findByUserIdAndLocalityId(user.getUserId(), localityId,sortByDateDesc).isEmpty()) {
			throw new ResourceExistsException("Duplicate review");
		}
		
		Review review = reviewMapper.dtoToReview(reviewDto);

		log.info("Saving new review");
		review.setImg((int) (Math.random() * 4) + 1);
		review.setDate(LocalDate.now());
		review.setUserId(user.getUserId());
		review.setUsername(user.getUsername());
		review.setLocalityId(locality.getLocalityId());
		review.setLocalityname(locality.getName());
		return reviewMapper.reviewToDto(this.reviewRepository.save(review));
	}

	@Override
	public List<ReviewDto> getAllReview() throws ResourceNotFoundException{

		List<Review> reviews = this.reviewRepository.findAllByOrderByDateDesc();

		if (reviews.isEmpty()) {
			throw new ResourceNotFoundException("Reviews not found");
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> reviewMapper.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getRecentReview() throws ResourceNotFoundException{

		List<Review> reviews = this.reviewRepository.findTop10ByOrderByDateDesc();

		if (reviews.isEmpty()) {
			throw new ResourceNotFoundException("Reviews not found");
		}

		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> reviewMapper.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getAllReviewByLocality(Long localityId) throws ResourceNotFoundException,
			IllegalArgumentException{

		List<Review> reviews = reviewRepository.findByLocalityId(localityId,sortByDateDesc);

		if (reviews.isEmpty()) {
			throw new ResourceNotFoundException("Reviews not found for selected locality");
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> reviewMapper.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}

	@Override
	public List<ReviewDto> getAllReviewByUser(String token) throws ResourceNotFoundException,
		IllegalArgumentException{
		
		UserDto user = this.fetchService.validateUser(token);
		
		List<Review> reviews = reviewRepository.findByUserId(user.getUserId(), sortByDateDesc);

		if (reviews.isEmpty()) {
			throw new ResourceNotFoundException("Reviews not found");
		}
		
		List<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> reviewMapper.reviewToDto(review)).collect(Collectors.toList());
		
		return reviewDtos;
	}
	
	@Override
	public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId, String token) 
	throws ResourceNotFoundException, IllegalArgumentException, NotAuthorizedException{
		
		UserDto validateUser = this.fetchService.validateUser(token);
		
		if(reviewDto == null) {
			throw new IllegalArgumentException("No review body found for update");
		}

		Optional<Review> searchedReview = reviewRepository.findById(reviewId);
		
		
		if (searchedReview.isEmpty()) {
			throw new ResourceNotFoundException("Review not found");
		}
		
		if(searchedReview.get().getUserId() != validateUser.getUserId()) {
			throw new NotAuthorizedException("User is unauthorized");
		}


		Review updatedReview = searchedReview.get();
		
		updatedReview.setContent(reviewDto.getContent() == null ?
				updatedReview.getContent() : reviewDto.getContent());
		
		//updatedReview.setDate(LocalDate.now());

		return reviewMapper.reviewToDto(this.reviewRepository.save(updatedReview));
	}

	@Override
	public Boolean deleteReview(Long reviewId, String token) throws ResourceNotFoundException,
	NotAuthorizedException{
		
		UserDto validateUser = this.fetchService.validateUser(token);
		
		Optional<Review> doesReviewExist = reviewRepository.findById(reviewId);

		if (doesReviewExist.isEmpty()) {
			throw new ResourceNotFoundException("Review not found");
		}
		
		if(doesReviewExist.get().getUserId() != validateUser.getUserId()) {
			throw new NotAuthorizedException("User is unauthorized");
		}
		
		reviewRepository.deleteById(reviewId);
		return true;
	}

	@Override
	public Boolean deleteReviewsByLocality(Long localityId, String token)throws ResourceNotFoundException,
			NotAuthorizedException{

		UserDto validateUser = this.fetchService.validateUser(token);

		List<Review> doesReviewExist = reviewRepository.findByLocalityId(localityId, sortByDateDesc);

		if (doesReviewExist.isEmpty()) {
			throw new ResourceNotFoundException("Review not found");
		}

		reviewRepository.deleteByLocalityId(localityId);
		return true;
	}


}
