package com.example.election.controller;

import com.example.election.dto.CandidateDTO;


import com.example.election.exception.UserNotFoundException;
import com.example.election.service.CandidateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CandidateControllerTest {

    @Mock
    private CandidateService candidateService;


    @InjectMocks
    private CandidateController candidateController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateCandidateSuccess() {
        CandidateDTO dto = new CandidateDTO();
        CandidateDTO createdDto = new CandidateDTO();

        when(candidateService.createCandidate(any(CandidateDTO.class))).thenReturn(createdDto);

        ResponseEntity<?> response = candidateController.createCandidate(dto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("age must be Greater and Equal to 25", response.getBody());
    }



    @Test
    public void testUpdateCandidateSuccess() throws UserNotFoundException {
        CandidateDTO dto = new CandidateDTO();
        dto.setCandidateId(1);

        when(candidateService.getCandidateById(anyString())).thenReturn(dto);
        when(candidateService.updateCandidate(anyString(), any(CandidateDTO.class))).thenReturn(dto);

        ResponseEntity<CandidateDTO> response = candidateController.updateCandidate(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testUpdateCandidateUserNotFound() throws UserNotFoundException {
        CandidateDTO dto = new CandidateDTO();
        dto.setCandidateId(1);

        when(candidateService.getCandidateById(anyString())).thenThrow(new UserNotFoundException("Candidate not found"));

        try {
            candidateController.updateCandidate(1, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Candidate not found", e.getMessage());
        }
    }

    @Test
    public void testGetCandidateByIdSuccess() throws UserNotFoundException {
        CandidateDTO candidate = new CandidateDTO();
        when(candidateService.getCandidateById(anyString())).thenReturn(candidate);

        ResponseEntity<CandidateDTO> response = candidateController.getCandidateById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(candidate, response.getBody());
    }

    @Test
    public void testGetCandidateByIdNotFound() throws UserNotFoundException {
        when(candidateService.getCandidateById(anyString())).thenThrow(new UserNotFoundException("Candidate not found"));

        try {
            candidateController.getCandidateById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Candidate not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteCandidateSuccess() throws UserNotFoundException {
        doNothing().when(candidateService).deleteCandidate(anyString());

        ResponseEntity<Void> response = candidateController.deleteCandidate(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCandidateNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("candidate not found")).when(candidateService).deleteCandidate(anyString());

        try {
            candidateController.deleteCandidate(999);
        } catch (UserNotFoundException e) {
            assertEquals("candidate not found", e.getMessage());
        }
    }
}


