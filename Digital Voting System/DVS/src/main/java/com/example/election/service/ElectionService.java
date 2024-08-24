package com.example.election.service;

import com.example.election.dto.ElectionDTO;
import com.example.election.entity.ElectionEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.ElectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {
    @Autowired
    private ElectionRepository electionRepository;

    // Convert entity to DTO
    private ElectionDTO convertEntityToDTO(ElectionEntity electionEntity) {
        ElectionDTO electionDTO = new ElectionDTO();
        BeanUtils.copyProperties(electionEntity, electionDTO);
        return electionDTO;
    }

    // Convert DTO to entity
    private ElectionEntity convertDTOToEntity(ElectionDTO electionDTO) {
        ElectionEntity electionEntity = new ElectionEntity();
        BeanUtils.copyProperties(electionDTO, electionEntity);
        return electionEntity;
    }

    // Create election
    public ElectionDTO createElection(ElectionDTO electionDTO) {
        ElectionEntity electionEntity = convertDTOToEntity(electionDTO);
        electionRepository.save(electionEntity);
        return convertEntityToDTO(electionEntity);
    }

    // Get election by ID
    public ElectionDTO getElectionById(String electionId) throws UserNotFoundException {
            Optional<ElectionEntity> optionalElection = electionRepository.findById(Long.valueOf(Integer.valueOf(electionId)));
            if (optionalElection.isPresent()) {
                return convertEntityToDTO(optionalElection.get());
            } else {
                throw new UserNotFoundException("User not found with id " + electionId);
            }
        }

    // Get all elections
    public List<ElectionDTO> getAllElections() {
        List<ElectionEntity> electionList = electionRepository.findAll();
        List<ElectionDTO> electionDTOList = new ArrayList<>();
        for (ElectionEntity electionEntity : electionList) {
            electionDTOList.add(convertEntityToDTO(electionEntity));
        }
        return electionDTOList;
    }

    // Update election
    public ElectionDTO updateElection(String electionId, ElectionDTO electionDTO) throws UserNotFoundException {
            Optional<ElectionEntity> optionalElection = electionRepository.findById(Long.valueOf(Integer.valueOf(electionId)));
            if (optionalElection.isPresent()) {
                ElectionEntity electionEntity = optionalElection.get();
                BeanUtils.copyProperties(electionDTO, electionEntity, "electionId");
                electionRepository.save(electionEntity);
                return convertEntityToDTO(electionEntity);
            } else {
                throw new UserNotFoundException("User not found with id " + electionId);
            }
        }


        // Delete election
        public void deleteElection(String electionId) throws UserNotFoundException {
            Optional<ElectionEntity> optionalUser = electionRepository.findById(Long.valueOf(Integer.valueOf(electionId)));
            if (optionalUser.isPresent()) {
                electionRepository.deleteById(Long.valueOf(Integer.valueOf(electionId)));
            } else {
                throw new UserNotFoundException("User not found with id " + electionId);
            }
        }
}
