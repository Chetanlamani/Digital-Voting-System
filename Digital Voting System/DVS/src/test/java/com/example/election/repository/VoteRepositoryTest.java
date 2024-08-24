package com.example.election.repository;

import com.example.election.entity.CandidateEntity;
import com.example.election.entity.ElectionEntity;
import com.example.election.entity.VoteEntity;
import com.example.election.entity.VoterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class VoteRepositoryTest {

    @Mock
    private VoteRepository voteRepository;

    private VoteEntity voteEntity;
    private VoterEntity voterEntity;
    private CandidateEntity candidateEntity;
    private ElectionEntity electionEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        voterEntity = new VoterEntity();
        voterEntity.setVoterId(1L);
        voterEntity.setVoterName("John Doe");

        candidateEntity=new CandidateEntity();
         candidateEntity.setCandidateId(3);
         candidateEntity.setCandidateName("suresh");

         electionEntity=new ElectionEntity();
         electionEntity.setElectionId(1);
         electionEntity.setName("MP");
        

        voteEntity = new VoteEntity();
        voteEntity.setVoteId(1L);
        voteEntity.setCandidateEntity(candidateEntity);
        voteEntity.setVoterEntity(voterEntity);
        voteEntity.setElectionEntity(electionEntity);
        voteEntity.setTimestamp(LocalDateTime.now());
    }

    @Test
    public void testSaveVote() {
        when(voteRepository.save(any(VoteEntity.class))).thenReturn(voteEntity);

        VoteEntity savedVote = voteRepository.save(voteEntity);

        assertNotNull(savedVote);
        assertEquals(voteEntity.getVoteId(), savedVote.getVoteId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(voteEntity));

        Optional<VoteEntity> foundVote = voteRepository.findById(1L);

        assertTrue(foundVote.isPresent());
        assertEquals(voteEntity.getVoteId(), foundVote.get().getVoteId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(voteRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<VoteEntity> foundVote = voteRepository.findById(2L);

        assertFalse(foundVote.isPresent());
    }




    @Test
    public void testDeleteVote() {
        doNothing().when(voteRepository).delete(voteEntity);

        voteRepository.delete(voteEntity);

        verify(voteRepository, times(1)).delete(voteEntity);
    }

    @Test
    public void testFindByVoterEntity_voterIdSuccess() {
        List<VoteEntity> vote = new ArrayList<>();
        vote.add(voteEntity);

        when(voteRepository.findByVoterEntity_voterId(1L)).thenReturn(vote);

        List<VoteEntity> foundVotes = voteRepository.findByVoterEntity_voterId(1L);

        assertNotNull(foundVotes);
        assertFalse(foundVotes.isEmpty());
        assertEquals(1, foundVotes.size());
        assertEquals(voteEntity.getVoteId(), foundVotes.get(0).getVoteId());
    }

    @Test
    public void testFindByVoterEntity_voterIdNotFound() {
        when(voteRepository.findByVoterEntity_voterId(2L)).thenReturn(new ArrayList<>());

        List<VoteEntity> foundVotes = voteRepository.findByVoterEntity_voterId(2L);

        assertNotNull(foundVotes);
        assertTrue(foundVotes.isEmpty());
    }


    @Test
    public void testFindByCandidateEntity_candidateIdSuccess() {
        List<VoteEntity> vote = new ArrayList<>();
        vote.add(voteEntity);

        when(voteRepository.findByCandidateEntity_candidateId(1L)).thenReturn(vote);

        List<VoteEntity> foundVotes = voteRepository.findByCandidateEntity_candidateId(1L);

        assertNotNull(foundVotes);
        assertFalse(foundVotes.isEmpty());
        assertEquals(1, foundVotes.size());
        assertEquals(voteEntity.getVoteId(), foundVotes.get(0).getVoteId());
    }

    @Test
    public void testFindByCandidateEntity_candidateIdNotFound() {
        when(voteRepository.findByCandidateEntity_candidateId(2L)).thenReturn(new ArrayList<>());

        List<VoteEntity> foundVotes = voteRepository.findByCandidateEntity_candidateId(2L);

        assertNotNull(foundVotes);
        assertTrue(foundVotes.isEmpty());
    }

}
