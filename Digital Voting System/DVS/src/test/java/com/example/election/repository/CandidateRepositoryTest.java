package com.example.election.repository;

import com.example.election.entity.CandidateEntity;
import com.example.election.entity.UserEntity;
import com.example.election.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CandidateRepositoryTest {

    @Mock
    private CandidateRepository candidateRepository;

    private CandidateEntity candidateEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.CANDIDATE);

        candidateEntity = new CandidateEntity();
        candidateEntity.setCandidateId(1L);
        candidateEntity.setUserEntity(userEntity);
        candidateEntity.setCandidateName("Jane Doe");
        candidateEntity.setParty("Independent");
        candidateEntity.setAge(45);
        candidateEntity.setManifesto("10 years in public service");
    }

    @Test
    public void testSaveCandidate() {
        when(candidateRepository.save(any(CandidateEntity.class))).thenReturn(candidateEntity);

        CandidateEntity savedCandidate = candidateRepository.save(candidateEntity);

        assertNotNull(savedCandidate);
        assertEquals(candidateEntity.getCandidateId(), savedCandidate.getCandidateId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));

        Optional<CandidateEntity> foundCandidate = candidateRepository.findById(1L);

        assertTrue(foundCandidate.isPresent());
        assertEquals(candidateEntity.getCandidateId(), foundCandidate.get().getCandidateId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(candidateRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<CandidateEntity> foundCandidate = candidateRepository.findById(2L);

        assertFalse(foundCandidate.isPresent());
    }

    @Test
    public void testDeleteCandidate() {
        doNothing().when(candidateRepository).delete(candidateEntity);

        candidateRepository.delete(candidateEntity);

        verify(candidateRepository, times(1)).delete(candidateEntity);
    }
}
