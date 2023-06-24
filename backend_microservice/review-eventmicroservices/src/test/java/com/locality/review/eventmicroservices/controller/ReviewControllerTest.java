package com.locality.review.eventmicroservices.controller;

import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.service.ReviewService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController controllerUnderTest;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    public void givenReviewBody_whenSaveReview_returnSavedReview() {
        //Mock
        ReviewDto input = ReviewDto.builder().content("content").build();
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();

        //Stubbing
        when(reviewService.saveReview(input, token, 1L)).thenReturn(savedReview);

        //Asserting
        ResponseEntity<ReviewDto> response = controllerUnderTest.saveReview(input, token, 1L);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void givenInvalidObject_whenSaveReview_thenFailValidation() {
        //Mock
        ReviewDto input = ReviewDto.builder().build();

        //Asserting
        Set<ConstraintViolation<ReviewDto>> errors = validator.validate(input);
        assertThat(errors).hasSize(1);
    }

    @Test
    public void whenGetAllReview_thenReturnAllReviews() {
        //Mock
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();
        //Stubbing
        when(reviewService.getAllReview()).thenReturn(Arrays.asList(savedReview));

        //Asserting
        ResponseEntity<List<ReviewDto>> response = controllerUnderTest.findAllReview();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetRecentReview_thenReturnRecentReviews() {
        //Mock
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();
        //Stubbing
        when(reviewService.getRecentReview()).thenReturn(Arrays.asList(savedReview));

        //Asserting
        ResponseEntity<List<ReviewDto>> response = controllerUnderTest.findRecentReview();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetAllReviewByUser_thenReturnAllReviews() {
        //Mock
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();
        //Stubbing
        when(reviewService.getAllReviewByUser(token)).thenReturn(Arrays.asList(savedReview));

        //Asserting
        ResponseEntity<List<ReviewDto>> response = controllerUnderTest.findReviewByUser(token);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenInvalidToken_whenGetAllReviewByUser_thenThrowException(){

        //Stubbing
        when(reviewService.getAllReviewByUser(token)).thenThrow(NotAuthorizedException.class);

        // Asserting
        assertThrows(NotAuthorizedException.class,
                () -> controllerUnderTest.findReviewByUser(token));
    }

    @Test
    public void whenGetAllReviewByLocality_thenReturnAllReviews() {
        //Mock
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();
        //Stubbing
        when(reviewService.getAllReviewByLocality(1L)).thenReturn(Arrays.asList(savedReview));

        //Asserting
        ResponseEntity<List<ReviewDto>> response = controllerUnderTest.findReviewByLocality(String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenInvalidId_whenGetAllReviewByLocality_thenThrowException(){
        //Stubbing
        when(reviewService.getAllReviewByLocality(1L)).thenThrow(ResourceNotFoundException.class);

        // Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> controllerUnderTest.findReviewByLocality(String.valueOf(1L)));
    }

    @Test
    public void whenUpdateReview_thenReturnUpdatedReview() {
        //Mock
        ReviewDto input = ReviewDto.builder().content("content").build();
        ReviewDto savedReview = ReviewDto.builder().reviewId(1L).img(1).date(LocalDate.parse("2020-01-01"))
                .userId(1L).username("dummy").localityId(1L).localityname("dummy-place").build();

        //Stubbing
        when(reviewService.updateReview(input, 1L, token)).thenReturn(savedReview);

        //Asserting
        ResponseEntity<ReviewDto> response = controllerUnderTest.updateReview(input,String.valueOf(1L), token);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void givenInvalidToken_whenUpdateReview_thenThrowException(){

        //Stubbing
        when(reviewService.updateReview(null, 1L, token)).thenThrow(NotAuthorizedException.class);

        // Asserting
        assertThrows(NotAuthorizedException.class,
                () -> controllerUnderTest.updateReview(null, String.valueOf(1L), token));
    }

    @Test
    public void whenDeleteReview_thenReturnMessage() {
        //Stubbing
        when(reviewService.deleteReview(1L, token)).thenReturn(true);

        //Asserting
        ResponseEntity<?> response = controllerUnderTest.deleteReview(token, String.valueOf(1L));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
