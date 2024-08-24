package com.example.election.service;

import com.example.election.dto.VoteDTO;
import com.example.election.entity.VoteEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVote() {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(1);

        VoteEntity voteEntity = new VoteEntity();
        BeanUtils.copyProperties(voteDTO, voteEntity);

        when(voteRepository.save(any(VoteEntity.class))).thenReturn(voteEntity);

        VoteDTO createdVote = voteService.createVote(voteDTO);

        assertNotNull(createdVote);
        assertEquals(voteDTO.getVoteId(), createdVote.getVoteId());
        verify(voteRepository, times(1)).save(any(VoteEntity.class));
    }

    @Test
    void testGetVoteById_Success() throws  UserNotFoundException {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteId(1);

        when(voteRepository.findById(1L)).thenReturn(Optional.of(voteEntity));

        VoteDTO voteDTO = voteService.getVoteById("1");

        assertNotNull(voteDTO);
        assertEquals(voteEntity.getVoteId(), voteDTO.getVoteId());
        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVoteById_VoterNotFoundException() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voteService.getVoteById("1"));
        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllVote() {
        List<VoteEntity> voteEntities = new ArrayList<>();
        VoteEntity voteEntity1 = new VoteEntity();
        voteEntity1.setVoteId(1);
        VoteEntity voteEntity2 = new VoteEntity();
        voteEntity2.setVoteId(2);
        voteEntities.add(voteEntity1);
        voteEntities.add(voteEntity2);

        when(voteRepository.findAll()).thenReturn(voteEntities);

        List<VoteDTO> voteDTOs = voteService.getAllVote();

        assertNotNull(voteDTOs);
        assertEquals(2, voteDTOs.size());
        verify(voteRepository, times(1)).findAll();
    }


    @Test
    void testUpdateVote_Success() throws  UserNotFoundException {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteId(1);

        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(1);

        when(voteRepository.findById(1L)).thenReturn(Optional.of(voteEntity));
        when(voteRepository.save(any(VoteEntity.class))).thenReturn(voteEntity);

        VoteDTO updatedVote = voteService.updateVote("1", voteDTO);

        assertNotNull(updatedVote);
        assertEquals(voteDTO.getVoteId(), updatedVote.getVoteId());
        verify(voteRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).save(any(VoteEntity.class));
    }

    @Test
    void testUpdateVote_VoterNotFoundException() {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(1);

        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voteService.updateVote("1", voteDTO));
        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteVote_Success() throws UserNotFoundException {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteId(1);

        when(voteRepository.findById(1L)).thenReturn(Optional.of(voteEntity));
        doNothing().when(voteRepository).deleteById(1L);

        voteService.deleteVote("1");

        verify(voteRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVote_VoterNotFoundException() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voteService.deleteVote("1"));
        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllVotesForVoter() {
        List<VoteEntity> voteEntities = new ArrayList<>();
        VoteEntity voteEntity1 = new VoteEntity();
        voteEntity1.setTimestamp(LocalDateTime.now());
        VoteEntity voteEntity2 = new VoteEntity();
        voteEntity1.setTimestamp(LocalDateTime.now());
        voteEntities.add(voteEntity1);
        voteEntities.add(voteEntity2);

        when(voteRepository.findByVoterEntity_voterId(1)).thenReturn(voteEntities);

        List<VoteDTO> voteDTOList = voteService.getAllVoteForVoter(1);

        assertNotNull(voteDTOList);
        assertEquals(2, voteDTOList.size());
        verify(voteRepository, times(1)).findByVoterEntity_voterId(1);
    }

    @Test
    void testGetAllVotesForCandidate() {
        List<VoteEntity> voteEntities = new ArrayList<>();
        VoteEntity voteEntity1 = new VoteEntity();
        voteEntity1.setTimestamp(LocalDateTime.now());
        VoteEntity voteEntity2 = new VoteEntity();
        voteEntity1.setTimestamp(LocalDateTime.now());
        voteEntities.add(voteEntity1);
        voteEntities.add(voteEntity2);

        when(voteRepository.findByCandidateEntity_candidateId(1)).thenReturn(voteEntities);

        List<VoteDTO> voteDTOList = voteService.getAllVoteForCandidate(1);

        assertNotNull(voteDTOList);
        assertEquals(2, voteDTOList.size());
        verify(voteRepository, times(1)).findByCandidateEntity_candidateId(1);
    }
}
