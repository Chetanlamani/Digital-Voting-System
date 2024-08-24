package com.example.election.controller;

import com.example.election.dto.ElectionDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.ElectionService;
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

public class ElectionControllerTest {
  
    @Mock
    private ElectionService electionService;

    @InjectMocks
    private ElectionController electionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetAllElectionsSuccess() {
        ElectionDTO dto1 = new ElectionDTO();
        ElectionDTO dto2 = new ElectionDTO();
        List<ElectionDTO> elections = Arrays.asList(dto1, dto2);

        when(electionService.getAllElections()).thenReturn(elections);

        ResponseEntity<List<ElectionDTO>> response = electionController.getAllElections();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(elections, response.getBody());
    }


    @Test
    public void testGetElectionByIdSuccess() throws UserNotFoundException {
        ElectionDTO election = new ElectionDTO();
        when(electionService.getElectionById(anyString())).thenReturn(election);

        ResponseEntity<ElectionDTO> response = electionController.getElectionById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(election, response.getBody());
    }

    @Test
    public void testGetElectionByIdNotFound() throws UserNotFoundException {
        when(electionService.getElectionById(anyString())).thenThrow(new UserNotFoundException("Election not found"));

        try {
            electionController.getElectionById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Election not found", e.getMessage());
        }
    }

    @Test
    public void testCreateElectionSuccess() {
        ElectionDTO dto = new ElectionDTO();
        ElectionDTO createdDto = new ElectionDTO();

        when(electionService.createElection(any(ElectionDTO.class))).thenReturn(createdDto);

        ResponseEntity<ElectionDTO> response = electionController.createElection(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }


    @Test
    public void testUpdateElectionSuccess() throws UserNotFoundException {
        ElectionDTO dto = new ElectionDTO();
        dto.setElectionId(1);
        ElectionDTO updatedDto = new ElectionDTO();

        when(electionService.getElectionById(anyString())).thenReturn(dto);
        when(electionService.updateElection(anyString(), any(ElectionDTO.class))).thenReturn(updatedDto);

        ResponseEntity<ElectionDTO> response = electionController.updateElection(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateElectionNotFound() throws UserNotFoundException {
        ElectionDTO dto = new ElectionDTO();

        when(electionService.getElectionById(anyString())).thenThrow(new UserNotFoundException("Election not found"));

        try {
            electionController.updateElection(999, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Election not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteElectionSuccess() throws UserNotFoundException {
        doNothing().when(electionService).deleteElection(anyString());

        ResponseEntity<Void> response = electionController.deleteElection(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteElectionNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("election not found")).when(electionService).deleteElection(anyString());

        try {
            electionController.deleteElection(999);
        } catch (UserNotFoundException e) {
            assertEquals("election not found", e.getMessage());
        }
    }
}
