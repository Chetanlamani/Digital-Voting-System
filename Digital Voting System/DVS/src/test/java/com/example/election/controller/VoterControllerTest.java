package com.example.election.controller;

import com.example.election.dto.*;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class VoterControllerTest {

    @Mock
    private VoterService voterService;

    @InjectMocks
    private VoterController voterController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVoterSuccess() {
        VoterDTO dto = new VoterDTO();

        VoterDTO createdDto = new VoterDTO();

        when(voterService.createVoter(any(VoterDTO.class))).thenReturn(createdDto);

        ResponseEntity<?> response = voterController.createVoter(dto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Age must be Greater than or Equal to 18", response.getBody());

    }


    @Test
    public void testUpdateVoterSuccess() throws UserNotFoundException {
        VoterDTO dto = new VoterDTO();
        dto.setVoterId(1);
        VoterDTO updatedDto = new VoterDTO();

        when(voterService.getVoterById(anyString())).thenReturn(dto);
        when(voterService.updateVoter(anyString(), any(VoterDTO.class))).thenReturn(updatedDto);

        ResponseEntity<VoterDTO> response = voterController.updateVoter(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateVoterNotFound() throws UserNotFoundException {
        VoterDTO dto = new VoterDTO();

        when(voterService.getVoterById(anyString())).thenThrow(new UserNotFoundException("Voter not found"));

        try {
            voterController.updateVoter(999, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Voter not found", e.getMessage());
        }
    }

    @Test
    void testGetVoterByIdSuccess() throws UserNotFoundException {
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setVoterName("Test Voter");

        when(voterService.getVoterById(anyString())).thenReturn(voterDTO);

        ResponseEntity<VoterDTO> response = voterController.getVoterById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Voter", response.getBody().getVoterName());
        verify(voterService, times(1)).getVoterById(anyString());
    }

    @Test
    void testGetVoterByIdVoterNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Voter not found")).when(voterService).getVoterById(anyString());

        try {
            voterController.getVoterById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Voter not found", e.getMessage());
        }
    }

    @Test
    void testDeleteVoterSuccess() throws UserNotFoundException {
        doNothing().when(voterService).deleteVoter(anyString());

        ResponseEntity<Void> response = voterController.deleteVoter(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(voterService, times(1)).deleteVoter(anyString());
    }

    @Test
    void testDeleteVoterNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Voter not found")).when(voterService).deleteVoter(anyString());

        try {
            voterController.deleteVoter(999);
        } catch (UserNotFoundException e) {
            assertEquals("Voter not found", e.getMessage());
        }
    }


}



