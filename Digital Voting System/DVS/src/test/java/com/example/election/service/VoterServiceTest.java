package com.example.election.service;

import com.example.election.dto.VoterDTO;
import com.example.election.entity.UserEntity;
import com.example.election.entity.VoterEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.VoterRepository;
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

class VoterServiceTest {

    @Mock
    private VoterRepository voterRepository;

    @InjectMocks
    private VoterService voterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVoter() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.VOTER);
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setVoterId(1);
        voterDTO.setUserEntity(userEntity);
        voterDTO.setAge(18);
        voterDTO.setVoterName("ganesh");
        voterDTO.setPhoneNumber("7645355656");
        

        VoterEntity voterEntity = new VoterEntity();
        BeanUtils.copyProperties(voterDTO, voterEntity);

        when(voterRepository.save(any(VoterEntity.class))).thenReturn(voterEntity);

        VoterDTO savesVoter = voterService.createVoter(voterDTO);

        assertNotNull(savesVoter);
        assertEquals(voterDTO.getVoterId(), savesVoter.getVoterId());
        assertEquals(voterDTO.getUserEntity(), savesVoter.getUserEntity());
        verify(voterRepository, times(1)).save(any(VoterEntity.class));
    }


    @Test
    void testGetAllVoters() {
        List<VoterEntity> voterEntities = new ArrayList<>();
        VoterEntity voterEntity1 = new VoterEntity();
        voterEntity1.setVoterId(1);

        VoterEntity voterEntity2 = new VoterEntity();
        voterEntity2.setVoterId(2);
        voterEntities.add(voterEntity1);
        voterEntities.add(voterEntity2);

        when(voterRepository.findAll()).thenReturn(voterEntities);

        List<VoterDTO> voterDTOs = voterService.getAllVoter();

        assertNotNull(voterDTOs);
        assertEquals(2, voterDTOs.size());
        verify(voterRepository, times(1)).findAll();
    }

    @Test
    void testUpdateVoter_Success() throws UserNotFoundException {
        VoterEntity voterEntity = new VoterEntity();
        voterEntity.setVoterId(1);
        voterEntity.setAge(18);

        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setAge(18);

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voterEntity));
        when(voterRepository.save(any(VoterEntity.class))).thenReturn(voterEntity);

        VoterDTO updatedVoter = voterService.updateVoter("1", voterDTO);

        assertNotNull(updatedVoter);
        verify(voterRepository, times(1)).findById(1L);
        verify(voterRepository, times(1)).save(any(VoterEntity.class));
    }

    @Test
    void testUpdateVoter_VoterNotFoundException() {
        VoterDTO voterDTO = new VoterDTO();


        when(voterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voterService.updateVoter("1", voterDTO));
        verify(voterRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteVoter_Success() throws UserNotFoundException {
        VoterEntity voterEntity = new VoterEntity();
        voterEntity.setVoterId(1);

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voterEntity));
        doNothing().when(voterRepository).deleteById(1L);

        voterService.deleteVoter("1");

        verify(voterRepository, times(1)).findById(1L);
        verify(voterRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVoter_VoterNotFoundException() {
        when(voterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voterService.deleteVoter("1"));
        verify(voterRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVoterById_Success() throws UserNotFoundException {
        VoterEntity voterEntity = new VoterEntity();
        voterEntity.setVoterId(1);


        when(voterRepository.findById(1L)).thenReturn(Optional.of(voterEntity));

        VoterDTO voterDTO = voterService.getVoterById("1");

        assertNotNull(voterDTO);
        assertEquals(voterEntity.getVoterId(), voterDTO.getVoterId());
        verify(voterRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVoterById_VoterNotFoundException() {
        when(voterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> voterService.getVoterById("1"));
        verify(voterRepository, times(1)).findById(1L);
    }
}
