package com.example.election.service;

import com.example.election.dto.CandidateDTO;
import com.example.election.entity.CandidateEntity;
import com.example.election.entity.UserEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.CandidateRepository;
import com.example.election.resource.Role;
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

class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCandidate() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.CANDIDATE);
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateId(1);
        candidateDTO.setUserEntity(userEntity);
        candidateDTO.setCandidateName("John Doe");
        candidateDTO.setEmail("john.doe@example.com");
        

        CandidateEntity candidateEntity = new CandidateEntity();
        BeanUtils.copyProperties(candidateDTO, candidateEntity);

        when(candidateRepository.save(any(CandidateEntity.class))).thenReturn(candidateEntity);

        CandidateDTO savedCandidate = candidateService.createCandidate(candidateDTO);

        assertNotNull(savedCandidate);
        assertEquals(candidateDTO.getCandidateId(), savedCandidate.getCandidateId());
        assertEquals(candidateDTO.getUserEntity(), savedCandidate.getUserEntity());
        assertEquals(candidateDTO.getCandidateName(), savedCandidate.getCandidateName());
        assertEquals(candidateDTO.getEmail(), savedCandidate.getEmail());
    }
    

    @Test
    void testGetCandidateById_Success() throws UserNotFoundException {
        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setCandidateId(1);
        candidateEntity.setCandidateName("John Doe");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));

        CandidateDTO candidateDTO = candidateService.getCandidateById("1");

        assertNotNull(candidateDTO);
        assertEquals(candidateEntity.getCandidateName(), candidateDTO.getCandidateName());
        verify(candidateRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCandidateById_UserNotFoundException() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> candidateService.getCandidateById("1"));
        verify(candidateRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCandidates() {
        List<CandidateEntity> candidateEntities = new ArrayList<>();
        CandidateEntity candidateEntity1 = new CandidateEntity();
        candidateEntity1.setCandidateName("John Doe");
        CandidateEntity candidateEntity2 = new CandidateEntity();
        candidateEntity2.setCandidateName("Jane Doe");
        candidateEntities.add(candidateEntity1);
        candidateEntities.add(candidateEntity2);

        when(candidateRepository.findAll()).thenReturn(candidateEntities);

        List<CandidateDTO> candidateDTOList = candidateService.getAllCandidates();

        assertNotNull(candidateDTOList);
        assertEquals(2, candidateDTOList.size());
        verify(candidateRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCandidate_Success() throws UserNotFoundException {
        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setCandidateId(1);
        candidateEntity.setCandidateName("John Doe");

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateName("Updated Name");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));
        when(candidateRepository.save(any(CandidateEntity.class))).thenReturn(candidateEntity);

        CandidateDTO updatedCandidate = candidateService.updateCandidate("1", candidateDTO);

        assertNotNull(updatedCandidate);
        assertEquals(candidateDTO.getCandidateName(), updatedCandidate.getCandidateName());
        verify(candidateRepository, times(1)).findById(1L);
        verify(candidateRepository, times(1)).save(any(CandidateEntity.class));
    }

    @Test
    void testUpdateCandidate_UserNotFoundException() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateName("Updated Name");

        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> candidateService.updateCandidate("1", candidateDTO));
        verify(candidateRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCandidate_Success() throws UserNotFoundException {
        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setCandidateId(1);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));
        doNothing().when(candidateRepository).deleteById(1L);

        candidateService.deleteCandidate("1");

        verify(candidateRepository, times(1)).findById(1L);
        verify(candidateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCandidate_UserNotFoundException() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> candidateService.deleteCandidate("1"));
        verify(candidateRepository, times(1)).findById(1L);
    }
}
