package com.example.election.service;

import com.example.election.dto.VoterDTO;
import com.example.election.entity.VoterEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.VoterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoterService {
    @Autowired
    private VoterRepository voterRepository;

    // Convert entity to DTO
    private VoterDTO convertEntityToDTO(VoterEntity voterEntity) {
        VoterDTO voterDTO = new VoterDTO();
        BeanUtils.copyProperties(voterEntity, voterDTO);
        return voterDTO;
    }

    // Convert DTO to entity
    private VoterEntity convertDTOToEntity(VoterDTO voterDTO) {
        VoterEntity voterEntity = new VoterEntity();
        BeanUtils.copyProperties(voterDTO, voterEntity);
        return voterEntity;
    }

    // Register user
    public VoterDTO createVoter(VoterDTO voterDTO) {
        VoterEntity voterEntity = convertDTOToEntity(voterDTO);
        voterRepository.save(voterEntity);
        return convertEntityToDTO(voterEntity);
    }


    // Get all users
    public List<VoterDTO> getAllVoter() {
        List<VoterEntity> voterEntity_list = voterRepository.findAll();
        List<VoterDTO> voterDTO_list = new ArrayList<>();
        for (VoterEntity voterEntity : voterEntity_list) {
            voterDTO_list.add(convertEntityToDTO(voterEntity));
        }
        return voterDTO_list;
    }

    //
    // Update user
    public VoterDTO updateVoter(String voterId, VoterDTO voterDTO) throws UserNotFoundException {
        Optional<VoterEntity> optionalVoter = voterRepository.findById(Long.valueOf(Integer.valueOf(voterId)));
        if (optionalVoter.isPresent()) {
            VoterEntity voterEntity = optionalVoter.get();
            BeanUtils.copyProperties(voterDTO, voterEntity, "voterId");
            voterRepository.save(voterEntity);
            return convertEntityToDTO(voterEntity);
        } else {
            throw new UserNotFoundException("Voter not found with id " + voterId);
        }
    }

    // Delete user
    public void deleteVoter(String voterId) throws UserNotFoundException {
        Optional<VoterEntity> optionalVoter = voterRepository.findById(Long.valueOf(Integer.valueOf(voterId)));
        if (optionalVoter.isPresent()) {
            voterRepository.deleteById(Long.valueOf(Integer.valueOf(voterId)));
        } else {
            throw new UserNotFoundException("Voter not found with id " + voterId);
        }
    }

    // Get user by ID
    public VoterDTO getVoterById(String voterId) throws UserNotFoundException {
        Optional<VoterEntity> optionalVoter = voterRepository.findById(Long.valueOf(Integer.valueOf(voterId)));
        if (optionalVoter.isPresent()) {
            return convertEntityToDTO(optionalVoter.get());
        } else {
            throw new UserNotFoundException("Voter not found with id " + voterId);
        }
    }



}

