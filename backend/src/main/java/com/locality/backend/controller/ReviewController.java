package com.locality.backend.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.response.SuccessResponse;
import com.locality.backend.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/")
	public ResponseEntity<SuccessResponse> saveReview(@RequestBody Review review, @RequestParam Long userId,
				@RequestParam Long localityId){
		
		
		return new ResponseEntity<SuccessResponse>(SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED)
				.message("Review created successfully")
				.data(Map.of("review", reviewService.saveReview(review, userId, localityId)))
				.build(),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<SuccessResponse> findAllReview(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all reviews successfully")
				.data(Map.of("reviews", reviewService.getAllReview()))
				.build());
	}
	
	@GetMapping("/recent")
	public ResponseEntity<SuccessResponse> findRecentReview(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all reviews successfully")
				.data(Map.of("reviews", reviewService.getRecentReview()))
				.build());
	}
	
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<SuccessResponse> findReviewByUserid(@PathVariable String userId){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all reviews successfully")
				.data(Map.of("reviews", reviewService.getAllReviewByUser(Long.parseLong(userId))))
				.build());
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<SuccessResponse> findReviewByLocalityid(@PathVariable String localityId){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all reviews successfully")
				.data(Map.of("reviews", reviewService.getAllReviewByLocality(Long.parseLong(localityId))))
				.build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> updateReview(@RequestBody Review review, 
				@PathVariable String id){
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Review updated successfully")
				.data(Map.of("review", reviewService.updateReview(review, Long.parseLong(id))))
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> deleteReview(@PathVariable String id){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Review deleted successfully")
				.data(Map.of("deleted", reviewService.deleteReview(Long.parseLong(id))))
				.build());
	}
	
	
	
}
