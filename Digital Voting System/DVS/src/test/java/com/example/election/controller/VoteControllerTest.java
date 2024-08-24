package com.example.election.controller;

import com.example.election.dto.VoteDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.VoteService;
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

public class VoteControllerTest {


    @Mock
    private VoteService voteService;
    
    @InjectMocks
    private VoteController voteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testCreateVoteSuccess() {
        VoteDTO dto = new VoteDTO();
        VoteDTO createdDto = new VoteDTO();

        when(voteService.createVote(any(VoteDTO.class))).thenReturn(createdDto);

        ResponseEntity<VoteDTO> response = voteController.createVote(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllVotesSuccess() {
        VoteDTO dto1 = new VoteDTO();
        VoteDTO dto2 = new VoteDTO();
        List<VoteDTO> votes = Arrays.asList(dto1, dto2);

        when(voteService.getAllVote()).thenReturn(votes);

        ResponseEntity<List<VoteDTO>> response = voteController.getAllVote();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votes, response.getBody());
    }

    @Test
    public void testUpdateVoteSuccess() throws UserNotFoundException {
        VoteDTO dto = new VoteDTO();
        dto.setVoteId(1);
        VoteDTO updatedDto = new VoteDTO();

        when(voteService.getVoteById(anyString())).thenReturn(dto);
        when(voteService.updateVote(anyString(), any(VoteDTO.class))).thenReturn(updatedDto);

        ResponseEntity<VoteDTO> response = voteController.updateVote(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateVoteNotFound() throws UserNotFoundException {
        VoteDTO dto = new VoteDTO();

        when(voteService.getVoteById(anyString())).thenThrow(new UserNotFoundException("Vote not found"));

        try {
            voteController.updateVote(999, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Vote not found", e.getMessage());
        }
    }


    @Test
    public void testGetVoteByIdSuccess() throws UserNotFoundException {
        VoteDTO vote = new VoteDTO();
        when(voteService.getVoteById(anyString())).thenReturn(vote);

        ResponseEntity<VoteDTO> response = voteController.getVoteById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vote, response.getBody());
    }

    @Test
    public void testGetVoteByIdNotFound() throws UserNotFoundException {
        when(voteService.getVoteById(anyString())).thenThrow(new UserNotFoundException("Vote not found"));

        try {
            voteController.getVoteById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Vote not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteVoteSuccess() throws UserNotFoundException {
        doNothing().when(voteService).deleteVote(anyString());

        ResponseEntity<Void> response = voteController.deleteVote(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteVoteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("vote not found")).when(voteService).deleteVote(anyString());

        try {
            voteController.deleteVote(999);
        } catch (UserNotFoundException e) {
            assertEquals("vote not found", e.getMessage());
        }
    }

    @Test
    public void testGetAllVotesByVoterIdSuccess() {
        VoteDTO dto1 = new VoteDTO();
        VoteDTO dto2 = new VoteDTO();
        List<VoteDTO> votes = Arrays.asList(dto1, dto2);

        when(voteService.getAllVoteForVoter(anyLong())).thenReturn(votes);

        ResponseEntity<List<VoteDTO>> response = voteController.getAllVotesForVoter(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votes, response.getBody());
    }

    @Test
    public void testGetAllVotesByCandidateIdSuccess() {
        VoteDTO dto1 = new VoteDTO();
        VoteDTO dto2 = new VoteDTO();
        List<VoteDTO> votes = Arrays.asList(dto1, dto2);

        when(voteService.getAllVoteForCandidate(anyLong())).thenReturn(votes);

        ResponseEntity<List<VoteDTO>> response = voteController.getAllVotesCandidate(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votes, response.getBody());
    }

}
