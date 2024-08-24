package com.example.election.repository;

import com.example.election.entity.UserEntity;
import com.example.election.entity.VoterEntity;
import com.example.election.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VoterRepositoryTest {

    @Mock
    private VoterRepository voterRepository;

    private VoterEntity voterEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.VOTER);

        voterEntity = new VoterEntity();
        voterEntity.setVoterId(1L);
        voterEntity.setUserEntity(userEntity);
        voterEntity.setVoterName("John Doe");
        voterEntity.setEmail("john.doe@example.com");
    }

    @Test
    public void testSaveVoter() {
        when(voterRepository.save(any(VoterEntity.class))).thenReturn(voterEntity);

        VoterEntity savedVoter = voterRepository.save(voterEntity);

        assertNotNull(savedVoter);
        assertEquals(voterEntity.getVoterId(), savedVoter.getVoterId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(voterRepository.findById(1L)).thenReturn(Optional.of(voterEntity));

        Optional<VoterEntity> foundVoter = voterRepository.findById(1L);

        assertTrue(foundVoter.isPresent());
        assertEquals(voterEntity.getVoterId(), foundVoter.get().getVoterId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(voterRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<VoterEntity> foundVoter = voterRepository.findById(2L);

        assertFalse(foundVoter.isPresent());
    }

    @Test
    public void testDeleteVoter() {
        doNothing().when(voterRepository).delete(voterEntity);

        voterRepository.delete(voterEntity);

        verify(voterRepository, times(1)).delete(voterEntity);
    }
}
