package com.locality.review.eventmicroservices.service;

import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.exception.RestClientException;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.service.impl.FetchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FetchServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private LoadBalancerClient loadBalancerClient;

    @InjectMocks
    private FetchServiceImpl serviceUnderTest;

    private ServiceInstance serviceInstance;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

    @BeforeEach
    void setUp() {
        serviceInstance = mock(ServiceInstance.class);
    }

    @Test
    void givenValidToken_whenValidateAdmin_ReturnsTrue() {
        //Mock
        UserDto userDto = UserDto.builder().role("ADMIN").build();
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(userDto, HttpStatus.OK);

        //Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        UserDto userDto1 = serviceUnderTest.validateUser(token);
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();
        assertThat(userDto1).isNotNull();
    }

    @Test
    void givenInvalidToken_whenValidateUser_ThrowsNotAuthorizedException() {

        //Mock
        UserDto userDto = UserDto.builder().role("MEMBER").build();
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.validateUser(token));
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();

    }

    @Test
    void testValidateUser_WithRestClientException_ThrowsRestClientException() {
        // Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new RestClientException("Invalid token"));

        // Asserting
        assertThrows(RestClientException.class, () -> serviceUnderTest.validateUser("token"));
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();
    }

    @Test
    void givenLocalityId_whenGetLocality_ReturnsLocality() {
        //Mock
        Long localityId = 123L;
        String categoryServiceUrl = "http://category-service";
        String expectedUrl = categoryServiceUrl + "/locality/" + localityId;
        LocalityAndEventTypeDto locality = LocalityAndEventTypeDto.builder().build();
        ResponseEntity<LocalityAndEventTypeDto> responseEntity = new ResponseEntity<>(locality, HttpStatus.OK);

        // Stubbing
        when(loadBalancerClient.choose("CATEGORY-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create(categoryServiceUrl));
        when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), isNull(), eq(LocalityAndEventTypeDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        LocalityAndEventTypeDto actualLocality = serviceUnderTest.getLocality(localityId);
        verify(loadBalancerClient).choose("CATEGORY-SERVICE");
        verify(serviceInstance).getUri();
        assertThat(actualLocality).isNotNull();
        verify(restTemplate).exchange(eq(expectedUrl), eq(HttpMethod.GET), isNull(), eq(LocalityAndEventTypeDto.class));

    }

    @Test
    public void givenInvalidLocalityId_whenGetLocality_ThrowsResourceNotFoundException() {
        // Mock
        Long localityId = 123L;
        String categoryServiceUrl = "http://category-service";
        String expectedUrl = categoryServiceUrl + "/locality/" + localityId;
        ResponseEntity<LocalityAndEventTypeDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Stubbing
        when(loadBalancerClient.choose("CATEGORY-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create(categoryServiceUrl));
        when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), isNull(), eq(LocalityAndEventTypeDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getLocality(localityId));
    }

    @Test
    public void testGetLocality_WithRestClientException_ThrowsRestClientException() {
        // Mock
        Long localityId = 123L;
        String categoryServiceUrl = "http://category-service";
        String expectedUrl = categoryServiceUrl + "/locality/" + localityId;

        //Stubbing
        when(loadBalancerClient.choose("CATEGORY-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create(categoryServiceUrl));
        when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), isNull(), eq(LocalityAndEventTypeDto.class)))
                .thenThrow(new RestClientException("Locality not found"));

        // Asserting
        assertThrows(RestClientException.class, () -> serviceUnderTest.getLocality(localityId));
    }
}
