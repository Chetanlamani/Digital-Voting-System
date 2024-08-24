package com.example.election.controller;

import com.example.election.controller.AuthenticationController;
import com.example.election.entity.UserEntity;
import com.example.election.response.AuthenticationResponse;
import com.example.election.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        UserEntity request = new UserEntity();
        AuthenticationResponse response = new AuthenticationResponse("xyz");

        when(authenticationService.register(any(UserEntity.class))).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.register(request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testLoginSuccess() {
        UserEntity request = new UserEntity();
        AuthenticationResponse response = new AuthenticationResponse("xyz");

        when(authenticationService.login(any(UserEntity.class))).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.login(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        UserEntity request = new UserEntity();
        when(authenticationService.register(any(UserEntity.class))).thenThrow(new RuntimeException("User already exists"));

        try {
            authenticationController.register(request);
        } catch (RuntimeException e) {
            assertEquals("User already exists", e.getMessage());
        }
    }

    @Test
    public void testLoginUserNotFound() {
        UserEntity request = new UserEntity();
        when(authenticationService.login(any(UserEntity.class))).thenThrow(new RuntimeException("User not found"));

        try {
            authenticationController.login(request);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }
}
