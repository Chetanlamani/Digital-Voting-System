package com.example.election.repository;

import com.example.election.entity.AdminEntity;
import com.example.election.entity.ElectionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ElectionRepositoryTest {

    @Mock
    private ElectionRepository electionRepository;

    private ElectionEntity electionEntity;

    private AdminEntity adminEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminEntity=new AdminEntity();
        adminEntity.setAdminId(1L);
        adminEntity.setAdminName("suresh");

        electionEntity = new ElectionEntity();
        electionEntity.setElectionId(1L);
        electionEntity.setAdminEntity(adminEntity);
        electionEntity.setName("Presidential Election 2024");
        electionEntity.setStartDate(LocalDateTime.now());
        electionEntity.setEndDate(LocalDateTime.now());
        electionEntity.setDescription("The upcoming presidential election.");
    }

    @Test
    public void testSaveElection() {
        when(electionRepository.save(any(ElectionEntity.class))).thenReturn(electionEntity);

        ElectionEntity savedElection = electionRepository.save(electionEntity);

        assertNotNull(savedElection);
        assertEquals(electionEntity.getElectionId(), savedElection.getElectionId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(electionRepository.findById(1L)).thenReturn(Optional.of(electionEntity));

        Optional<ElectionEntity> foundElection = electionRepository.findById(1L);

        assertTrue(foundElection.isPresent());
        assertEquals(electionEntity.getElectionId(), foundElection.get().getElectionId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(electionRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<ElectionEntity> foundElection = electionRepository.findById(2L);

        assertFalse(foundElection.isPresent());
    }

    @Test
    public void testDeleteElection() {
        doNothing().when(electionRepository).delete(electionEntity);

        electionRepository.delete(electionEntity);

        verify(electionRepository, times(1)).delete(electionEntity);
    }
}
