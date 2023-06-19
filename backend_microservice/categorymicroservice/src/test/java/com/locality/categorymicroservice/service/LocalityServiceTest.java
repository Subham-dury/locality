package com.locality.categorymicroservice.service;

import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.ResourceExistsException;
import com.locality.categorymicroservice.exception.ResourceNotFoundException;
import com.locality.categorymicroservice.mapper.LocalityMapper;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.repository.LocalityRepository;
import com.locality.categorymicroservice.service.impl.LocalityServiceImpl;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocalityServiceTest {

    private ModelMapper modelMapper;
    @Mock
    private LocalityRepository localityRepository;

    @Mock
    private LocalityMapper localityMapper;

    @Mock
    private FetchUserService fetchUserService;

    @InjectMocks
    private LocalityServiceImpl serviceUnderTest;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void givenLocalityObject_whenSaveLocality_thenReturnLocalityDto() {

        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        Locality localityInput = modelMapper.map(inputDto, Locality.class);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        Locality savedLocality = Locality.builder()
                .localityId(1L)
                .name(localityInput.getName())
                .city(localityInput.getCity())
                .state(localityInput.getState())
                .img(1)
                .about(localityInput.getAbout())
                .build();
        LocalityDto expectedLocalityDto = modelMapper.map(savedLocality, LocalityDto.class);

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findByName(localityInput.getName())).thenReturn(null);
        when(localityMapper.dtoToLocality(inputDto)).thenReturn(localityInput);
        when(localityRepository.save(localityInput)).thenReturn(savedLocality);
        when(localityMapper.localityToDto(savedLocality)).thenReturn(expectedLocalityDto);

        //Asserting
        LocalityDto actualLocalityDto = serviceUnderTest.saveLocality(inputDto, token);
        assertThat(actualLocalityDto).isNotNull();
        assertEquals(expectedLocalityDto, actualLocalityDto);

        verify(fetchUserService, times(1)).checkIsUserAdmin(token);
        verify(localityRepository, times(1)).save(localityInput);
        verify(localityRepository, times(1)).findByName(localityInput.getName());

    }

    @Test
    public void givenLocalityObjectNotAuthorized_whenSaveLocality_throwNotAuthorizedException() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(false);
        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.saveLocality(inputDto, token));
    }

    @Test
    public void givenNullToken_whenSaveLocality_throwNotAuthorizedException() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(null)).thenReturn(false);
        //Asserting
        Exception exception = assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.saveLocality(inputDto, null));
        String expectedMessage = "User is not authorised";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenDuplicateLocality_whenSaveLocality_throwResourceExistsException() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        Locality existingLocality = Locality.builder()
                .localityId(1L)
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findByName(inputDto.getName())).thenReturn(existingLocality);

        //Asserting
        Exception exception = assertThrows(ResourceExistsException.class, () -> serviceUnderTest.saveLocality(inputDto, token));
        String expectedMessage = "Locality with name of " + inputDto.getName() + " exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void whenGetAllLocality_thenReturnAllLocalityList() {
        //Mock
        Locality locality1 = Locality.builder()
                .localityId(1L)
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        List<Locality> localityList = Arrays.asList(locality1);

        //Stubbing
        when(localityRepository.findAll()).thenReturn(localityList);
        //Asserting
        List<LocalityDto> actualLocalityList = serviceUnderTest.getAllLocality();
        assertThat(actualLocalityList).isNotNull();
        assertEquals(actualLocalityList.size(), 1);
        assertEquals(actualLocalityList.get(0), null);
        verify(localityRepository, times(1)).findAll();
    }

    @Test
    public void listNotFound_whenGetAllLocality_throwResourceNotFoundException() {
        //Stubbing
        when(localityRepository.findAll()).thenReturn(Collections.emptyList());
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getAllLocality());
        String expectedMessage = "Locality not found";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.equalsIgnoreCase(actualMessage));
    }

    @Test
    public void givenLocalityId_whenGetLocality_thenReturnLocalityDto() {
        //Mock
        Locality expectedLocality = Locality.builder()
                .localityId(1L)
                .name("Locality-test")
                .build();
        LocalityAndEventTypeDto expectedDto = LocalityAndEventTypeDto.builder().localityId(1L).name("Locality-test").build();

        //Stubbing
        when(localityRepository.findById(1L)).thenReturn(Optional.of(expectedLocality));
        when(localityMapper.localityToDtoUsingIdAndName(expectedLocality)).thenReturn(expectedDto);

        //Asserting
        LocalityAndEventTypeDto actualLocality = serviceUnderTest.getLocality(1L);
        assertThat(actualLocality).isNotNull();
        assertTrue(actualLocality.getName().equalsIgnoreCase("Locality-test"));

    }

    @Test
    public void givenInvalidLocalityID_whenGetLocality_ThrowResourceNotFoundException() {

        //Stubbing
        when(localityRepository.findById(2L)).thenReturn(Optional.empty());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getLocality(2L));
        String expectedMessage = "Locality not found";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.equalsIgnoreCase(actualMessage));

    }

    @Test
    public void givenLocalityBody_whenUpdateLocality_thenReturnUpdatedLocalityDto() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test-updated")
                .build();
        Locality existingLocality = Locality.builder()
                .localityId(1L)
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        Locality updatedLocality = Locality.builder()
                .localityId(1L)
                .name("Locality-test-updated")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        LocalityDto expectedLocalityDto = modelMapper.map(updatedLocality, LocalityDto.class);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findById(1L)).thenReturn(Optional.of(existingLocality));
        when(localityRepository.save(updatedLocality)).thenReturn(updatedLocality);
        when(localityMapper.localityToDto(updatedLocality)).thenReturn(expectedLocalityDto);

        //Asserting
        LocalityDto actualLocalityDto = serviceUnderTest.updateLocality(inputDto, 1L, token);
        assertThat(actualLocalityDto).isNotNull();
        assertEquals(actualLocalityDto, expectedLocalityDto);
    }

    @Test
    public void givenInvalidToken_whenUpdateLocality_throwNotAuthorizedException() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test-updated")
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(false);
        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.updateLocality(inputDto, 1L, token));
    }

    @Test
    public void givenInvalidNullBody_whenUpdateLocality_throwIllegalArgumentException() {
        //Mock
        LocalityDto inputDto = null;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        //Asserting
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.updateLocality(inputDto, 1L, token));
        String expectedMessage = "No locality body for update";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.equalsIgnoreCase(actualMessage));
    }

    @Test
    public void givenInvalidLocalityId_whenUpdateLocality_throwResourceNotFoundException() {
        //Mock
        LocalityDto inputDto = LocalityDto.builder()
                .name("Locality-test-updated")
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.updateLocality(inputDto, 1L, token));
    }

    @Test
    public void givenValidId_whenDeleteLocality_thenReturnTrue() {
        //Mock
        Locality locality = Locality.builder()
                .localityId(1L)
                .name("Locality-test")
                .city("test-city")
                .state("test-state")
                .about("This is a test locality")
                .img(1)
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findById(1L)).thenReturn(Optional.of(locality));
        //Asserting
        assertTrue(serviceUnderTest.deleteLocality(1L, token));
    }

    @Test
    public void givenInvalidToken_whenDeleteLocality_throwNotAuthorizedException() {
        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(false);
        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.deleteLocality(1L, token));
    }

    @Test
    public void givenInvalidId_whenDeleteLocality_throwResourceNotFoundException() {

        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stabbing
        when(fetchUserService.checkIsUserAdmin(token)).thenReturn(true);
        when(localityRepository.findById(1L)).thenReturn(Optional.empty());
        when(localityRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.deleteLocality(1L, token));
    }
}