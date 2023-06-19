package com.locality.categorymicroservice.service;

import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.RestClientException;
import com.locality.categorymicroservice.payload.UserDto;
import com.locality.categorymicroservice.service.impl.FetchUserServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FetchUserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private LoadBalancerClient loadBalancerClient;

    @InjectMocks
    private FetchUserServiceImpl serviceUnderTest;

    private ServiceInstance serviceInstance;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

    @BeforeEach
    void setUp() {
        serviceInstance = mock(ServiceInstance.class);
    }

    @Test
    void givenValidToken_whenCheckIsUserAdmin_ReturnsTrue() {
        //Mock
        UserDto userDto = UserDto.builder().role("ADMIN").build();
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(userDto, HttpStatus.OK);

        //Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        boolean isAdmin = serviceUnderTest.checkIsUserAdmin(token);
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();
        assertTrue(isAdmin);
    }

    @Test
    void givenInvalidToken_whenCheckIsUserAdmin_ThrowsNotAuthorizedException() {

        //Mock
        UserDto userDto = UserDto.builder().role("MEMBER").build();
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        // Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.checkIsUserAdmin(token));
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();

    }



    @Test
    void testCheckIsUserAdmin_WithRestClientException_ThrowsRestClientException() {
        // Stubbing
        when(loadBalancerClient.choose("USER-SERVICE")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8081"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new RestClientException("Invalid token"));

        // Asserting
        assertThrows(RestClientException.class, () -> serviceUnderTest.checkIsUserAdmin("token"));
        verify(loadBalancerClient).choose("USER-SERVICE");
        verify(serviceInstance).getUri();
    }
}
