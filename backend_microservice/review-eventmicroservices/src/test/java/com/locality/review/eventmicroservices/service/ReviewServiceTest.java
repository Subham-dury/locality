package com.locality.review.eventmicroservices.service;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.mapper.ReviewMapper;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.repository.ReviewRepository;
import com.locality.review.eventmicroservices.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubbing;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private FetchService fetchService;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl serviceUnderTest;

    private ModelMapper modelMapper;
    Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "date");
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJKb2huRG9lIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNjg3MTc1NzMxLCJleHAiOjE2ODcyNjIxMzF9.Z_i5xVeFaLIMmkxdvxEqwYH-fw3P6zvsbVKZvzAae64";

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void givenReviewBody_whenSaveReview_thenReturnSavedReview() {

        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test").build();
        Review review = modelMapper.map(inputReviewDto, Review.class);

        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        LocalityAndEventTypeDto locality = LocalityAndEventTypeDto.builder()
                .localityId(2L).name("Dummy-place").build();

        Review savedReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(user.getUserId()).username(user.getUsername())
                .localityId(locality.getLocalityId()).localityname(locality.getName()).build();
        ReviewDto expectedReview = modelMapper.map(savedReview, ReviewDto.class);

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(fetchService.getLocality(2L)).thenReturn(locality);
        when(reviewRepository.findByUserIdAndLocalityId(user.getUserId(),
                2L, sortByDateDesc)).thenReturn(Collections.emptyList());
        when(reviewMapper.dtoToReview(inputReviewDto)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(savedReview);
        when(reviewMapper.reviewToDto(savedReview)).thenReturn(expectedReview);

        //Asserting
        ReviewDto actualReviewDto = serviceUnderTest.saveReview(inputReviewDto, token, 2L);
        assertThat(actualReviewDto).isNotNull();
        assertEquals(actualReviewDto, expectedReview);

    }

    @Test
    public void givenInvalidToken_whenSaveReview_thenThrowNotAuthorizedException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.saveReview(inputReviewDto, token, 1L));

    }

    @Test
    public void givenInvalidLocalityId_whenSaveReview_thenThrowResourceNotFoundException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.getLocality(2L)).thenThrow(ResourceNotFoundException.class);

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.saveReview(inputReviewDto, token, 2L));

    }

    @Test
    public void givenDuplicateData_whenSaveReview_thenThrowResourceExistException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        LocalityAndEventTypeDto locality = LocalityAndEventTypeDto.builder()
                .localityId(2L).name("Dummy-place").build();

        Review existingReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(user.getUserId()).username(user.getUsername())
                .localityId(locality.getLocalityId()).localityname(locality.getName()).build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(fetchService.getLocality(2L)).thenReturn(locality);
        when(reviewRepository.findByUserIdAndLocalityId(user.getUserId(), 2L, sortByDateDesc))
                .thenReturn(Arrays.asList(existingReview));

        //Asserting
        ResourceExistsException exception = assertThrows(ResourceExistsException.class,
                () -> serviceUnderTest.saveReview(inputReviewDto, token, 2L));
        String expectedMessage = "Duplicate review";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(reviewRepository, times(1))
                .findByUserIdAndLocalityId(user.getUserId(), 2L, sortByDateDesc);
    }

    @Test
    public void whenGetAllReview_thenReturnAllReviews() {
        //Mock
        Review review1 = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username("dummy")
                .localityId(1L).localityname("dummy-place").build();

        //Stubbing
        when(reviewRepository.findAllByOrderByDateDesc()).thenReturn(Arrays.asList(review1));

        //Asserting
        List<ReviewDto> allReview = serviceUnderTest.getAllReview();
        assertThat(allReview).isNotNull();
        assertThat(allReview.size()).isEqualTo(1);
        assertThat(allReview.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllReview_thenThrowResourceNotFoundException() {

        //Stubbing
        when(reviewRepository.findAllByOrderByDateDesc()).thenReturn(Collections.emptyList());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllReview());
        String expectedMessage = "Reviews not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(reviewRepository, times(1)).findAllByOrderByDateDesc();

    }

    @Test
    public void whenGetRecentReview_thenReturnRecentReviews() {
        //Mock
        Review review1 = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username("dummy")
                .localityId(1L).localityname("dummy-place").build();

        //Stubbing
        when(reviewRepository.findTop10ByOrderByDateDesc()).thenReturn(Arrays.asList(review1));

        //Asserting
        List<ReviewDto> allReview = serviceUnderTest.getRecentReview();
        assertThat(allReview).isNotNull();
        assertThat(allReview.size()).isEqualTo(1);
        assertThat(allReview.get(0)).isNull();

    }

    @Test
    public void listNotFound_whenGetRecentReview_thenThrowResourceNotFoundException() {

        //Stubbing
        when(reviewRepository.findTop10ByOrderByDateDesc()).thenReturn(Collections.emptyList());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getRecentReview());
        String expectedMessage = "Reviews not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(reviewRepository, times(1)).findTop10ByOrderByDateDesc();

    }

    @Test
    public void givenLocalityId_whenGetAllReviewByLocality_thenReturnReviews() {
        //Mock
        Review review1 = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username("dummy")
                .localityId(1L).localityname("dummy-place").build();
        Long localityId = 1L;

        //Stubbing
        when(reviewRepository.findByLocalityId(localityId, sortByDateDesc)).thenReturn(Arrays.asList(review1));

        //Asserting
        List<ReviewDto> allReview = serviceUnderTest.getAllReviewByLocality(localityId);
        assertThat(allReview).isNotNull();
        assertThat(allReview.size()).isEqualTo(1);
        assertThat(allReview.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllReviewByLocality_thenThrowResourceNotFoundException() {

        //Mock
        Long localityId = 1L;

        //Stubbing
        when(reviewRepository.findByLocalityId(localityId, sortByDateDesc)).thenReturn(Collections.emptyList());
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllReviewByLocality(localityId));
        String expectedMessage = "Reviews not found for selected locality";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(reviewRepository, times(1)).findByLocalityId(localityId, sortByDateDesc);

    }

    @Test
    public void givenLocalityId_whenGetAllReviewByUser_thenReturnReviews() {
        //Mock
        Review review1 = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username("dummy")
                .localityId(1L).localityname("dummy-place").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findByUserId(user.getUserId(), sortByDateDesc)).thenReturn(Arrays.asList(review1));

        //Asserting
        List<ReviewDto> allReview = serviceUnderTest.getAllReviewByUser(token);
        assertThat(allReview).isNotNull();
        assertThat(allReview.size()).isEqualTo(1);
        assertThat(allReview.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllReviewByUser_thenThrowResourceNotFoundException() {

        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findByUserId(user.getUserId(), sortByDateDesc)).thenReturn(Collections.emptyList());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllReviewByUser(token));
        String expectedMessage = "Reviews not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(reviewRepository, times(1)).findByUserId(user.getUserId(), sortByDateDesc);

    }

    @Test
    public void givenReviewBody_whenUpdateReview_thenReturnUpdatedReview() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is test 2").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        Review existingReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(user.getUserId()).username(user.getUsername())
                .localityId(1L).localityname("dummy").build();

        Review updatedReview = Review.builder()
                .reviewId(1L).img(1).content("this is test 2").date(LocalDate.parse("2023-12-09"))
                .userId(user.getUserId()).username(user.getUsername())
                .localityId(1L).localityname("dummy").build();

        ReviewDto expectedReview = modelMapper.map(updatedReview, ReviewDto.class);

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(updatedReview)).thenReturn(updatedReview);
        when(reviewMapper.reviewToDto(updatedReview)).thenReturn(expectedReview);

        //Asserting
        ReviewDto actualReviewDto = serviceUnderTest.updateReview(inputReviewDto, 1L, token);
        assertEquals(actualReviewDto, expectedReview);

    }

    @Test
    public void givenInvalidToken_whenUpdateReview_thenThrowNotAuthorizedException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test 2").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class,
                () -> serviceUnderTest.updateReview(inputReviewDto, 1L, token));
    }

    @Test
    public void givenInvalidId_whenUpdateReview_thenThrowResourceNotFoundException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is a test 2").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();


        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.updateReview(inputReviewDto, 1L, token));

    }

    @Test
    public void givenNullBody_whenUpdateReview_thenThrowIllegalArgumentException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.updateReview(null, 1L, token));

    }

    @Test
    public void givenInvalidUser_thenThrowNotAuthorizedException() {
        //Mock
        ReviewDto inputReviewDto = ReviewDto.builder().content("this is test 2").build();
        UserDto user = UserDto.builder().userId(2L).username("dummy").role("MEMBER").build();

        Review existingReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username(user.getUsername())
                .localityId(1L).localityname("dummy").build();
        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.updateReview(inputReviewDto, 1L, token));


    }

    @Test
    public void givenReviewId_whenDeleteReview_returnTrue() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        Review existingReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(1L).username(user.getUsername())
                .localityId(1L).localityname("dummy").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));

        //Asserting
        Boolean actualResponse = serviceUnderTest.deleteReview(1L, token);
        assertTrue(actualResponse);
    }

    @Test
    public void givenInvalidToken_whenDeleteReview_thenThrowNOtAuthorizedException() {

        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.deleteReview(1L, token));

    }

    @Test
    public void givenInvalidReviewId_whenDeleteReview_thenThrowResourceNotFoundException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.deleteReview(1L, token));

    }

    @Test
    public void givenInvalidUserDetails_whenDeleteReview_thenThrowNotAuthorizedException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        Review existingReview = Review.builder()
                .reviewId(1L).img(1).content("this is test").date(LocalDate.parse("2023-12-09"))
                .userId(2L).username(user.getUsername())
                .localityId(1L).localityname("dummy").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.deleteReview(1L, token));

    }
}