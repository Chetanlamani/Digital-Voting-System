package com.example.election.service;

import com.example.election.dto.ElectionDTO;
import com.example.election.entity.ElectionEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.ElectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ElectionServiceTest {

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private ElectionService electionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateElection() {
        ElectionDTO electionDTO = new ElectionDTO();
        electionDTO.setName("General Election 2024");

        ElectionEntity electionEntity = new ElectionEntity();
        BeanUtils.copyProperties(electionDTO, electionEntity);

        when(electionRepository.save(any(ElectionEntity.class))).thenReturn(electionEntity);

        ElectionDTO createdElection = electionService.createElection(electionDTO);

        assertNotNull(createdElection);
        assertEquals(electionDTO.getName(), createdElection.getName());
        verify(electionRepository, times(1)).save(any(ElectionEntity.class));
    }

    @Test
    void testGetElectionById_Success() throws UserNotFoundException {
        ElectionEntity electionEntity = new ElectionEntity();
        electionEntity.setElectionId(1);
        electionEntity.setName("General Election 2024");

        when(electionRepository.findById(1L)).thenReturn(Optional.of(electionEntity));

        ElectionDTO electionDTO = electionService.getElectionById("1");

        assertNotNull(electionDTO);
        assertEquals(electionEntity.getName(), electionDTO.getName());
        verify(electionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetElectionById_UserNotFoundException() {
        when(electionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> electionService.getElectionById("1"));
        verify(electionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllElections() {
        List<ElectionEntity> electionEntities = new ArrayList<>();
        ElectionEntity electionEntity1 = new ElectionEntity();
        electionEntity1.setName("General Election 2024");
        ElectionEntity electionEntity2 = new ElectionEntity();
        electionEntity2.setName("Midterm Election 2022");
        electionEntities.add(electionEntity1);
        electionEntities.add(electionEntity2);

        when(electionRepository.findAll()).thenReturn(electionEntities);

        List<ElectionDTO> electionDTOList = electionService.getAllElections();

        assertNotNull(electionDTOList);
        assertEquals(2, electionDTOList.size());
        verify(electionRepository, times(1)).findAll();
    }

    @Test
    void testUpdateElection_Success() throws UserNotFoundException {
        ElectionEntity electionEntity = new ElectionEntity();
        electionEntity.setElectionId(1);
        electionEntity.setName("General Election 2024");

        ElectionDTO electionDTO = new ElectionDTO();
        electionDTO.setName("Updated Election");

        when(electionRepository.findById(1L)).thenReturn(Optional.of(electionEntity));
        when(electionRepository.save(any(ElectionEntity.class))).thenReturn(electionEntity);

        ElectionDTO updatedElection = electionService.updateElection("1", electionDTO);

        assertNotNull(updatedElection);
        assertEquals(electionDTO.getName(), updatedElection.getName());
        verify(electionRepository, times(1)).findById(1L);
        verify(electionRepository, times(1)).save(any(ElectionEntity.class));
    }

    @Test
    void testUpdateElection_UserNotFoundException() {
        ElectionDTO electionDTO = new ElectionDTO();
        electionDTO.setName("Updated Election");

        when(electionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> electionService.updateElection("1", electionDTO));
        verify(electionRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteElection_Success() throws UserNotFoundException {
        ElectionEntity electionEntity = new ElectionEntity();
        electionEntity.setElectionId(1);

        when(electionRepository.findById(1L)).thenReturn(Optional.of(electionEntity));
        doNothing().when(electionRepository).deleteById(1L);

        electionService.deleteElection("1");

        verify(electionRepository, times(1)).findById(1L);
        verify(electionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteElection_UserNotFoundException() {
        when(electionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> electionService.deleteElection("1"));
        verify(electionRepository, times(1)).findById(1L);
    }
}
