package com.locality.categorymicroservice.controller;

import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.ResourceNotFoundException;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;
import com.locality.categorymicroservice.service.LocalityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocalityControllerTest {

    @Mock
    private LocalityService localityService;

    @InjectMocks
    private LocalityController controllerUnderTest;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    public void givenLocalityObject_whenSaveLocality_returnSavedLocality() {
        //Mock
        LocalityDto locality = LocalityDto.builder().name("dummy").city("city").state("state").about("lorem 10").build();
        LocalityDto localityDto = LocalityDto.builder().localityId(1L).name("dummy").city("city").state("state").about("lorem 10").build();

        //Stabbing
        when(localityService.saveLocality(locality, token)).thenReturn(localityDto);

        //Asserting
        ResponseEntity<LocalityDto> response = controllerUnderTest.saveLocality(token, locality);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void givenInvalidObject_whenSaveLocality_thenFailValidation() {
        //Mock
        LocalityDto locality = LocalityDto.builder().build();

        //Asserting
        Set<ConstraintViolation<LocalityDto>> errors = validator.validate(locality);
        assertThat(errors).hasSize(4);
    }


    @Test
    public void whenGetAllLocality_thenReturnAllLocality() {
        //Mock
        LocalityDto localityDto = LocalityDto.builder().localityId(1L).name("dummy").city("city").state("state").about("lorem 10").build();

        //Stubbing
        when(localityService.getAllLocality()).thenReturn(Arrays.asList(localityDto));

        //Asserting
        ResponseEntity<List<LocalityDto>> response = controllerUnderTest.getAllLocality();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetLocality_thenReturnLocality() {

        //Mock
        LocalityAndEventTypeDto localityDto = LocalityAndEventTypeDto.builder().localityId(1L).name("dummy").build();

        //Stubbing
        when(localityService.getLocality(1L)).thenReturn(localityDto);

        //Asserting
        ResponseEntity<LocalityAndEventTypeDto> response = controllerUnderTest.getLocality(String.valueOf(1));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void givenInvalidId_whenGetLocality_thenThrowException(){

        when(localityService.getLocality(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> controllerUnderTest.getLocality(String.valueOf(1L)));
    }

    @Test
    public void whenUpdateLocality_thenReturnUpdatedLocality() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder().build();
        LocalityDto localityDto = LocalityDto.builder().localityId(1L).name("dummy").city("city").state("state").about("lorem 10").build();

        //Stubbing
        when(localityService.updateLocality(inputDto, 1L, token)).thenReturn(localityDto);

        //Asserting
        ResponseEntity<LocalityDto> response = controllerUnderTest.updateLocality(token, inputDto, String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void givenInvalidToken_whenUpdateLocality_thenThrowException() {

        //Stubbing
        when(localityService.updateLocality(null, 1L, token)).thenThrow(NotAuthorizedException.class);

        // Asserting
        assertThrows(NotAuthorizedException.class,
                () -> controllerUnderTest.updateLocality(token, null, String.valueOf(1L)));
    }

    @Test
    public void whenDeleteLocality_thenReturnMessage() {
        //Stubbing
        when(localityService.deleteLocality(1L, token)).thenReturn(true);

        //Asserting
        ResponseEntity<?> response = controllerUnderTest.deleteLocality(token, String.valueOf(1L));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
