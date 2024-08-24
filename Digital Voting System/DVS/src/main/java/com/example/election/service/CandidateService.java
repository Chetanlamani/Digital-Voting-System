package com.example.election.service;

import com.example.election.dto.CandidateDTO;
import com.example.election.entity.CandidateEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.CandidateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    // Convert entity to DTO
    private CandidateDTO convertEntityToDTO(CandidateEntity candidateEntity) {
        CandidateDTO candidateDTO = new CandidateDTO();
        BeanUtils.copyProperties(candidateEntity, candidateDTO);
        return candidateDTO;
    }

    // Convert DTO to entity
    private CandidateEntity convertDTOToEntity(CandidateDTO candidateDTO) {
        CandidateEntity candidateEntity = new CandidateEntity();
        BeanUtils.copyProperties(candidateDTO, candidateEntity);
        return candidateEntity;
    }

    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        CandidateEntity candidateEntity = convertDTOToEntity(candidateDTO);
        candidateRepository.save(candidateEntity);
        return convertEntityToDTO(candidateEntity);
    }


    // Get candidate by ID
    public CandidateDTO getCandidateById(String candidateId) throws UserNotFoundException {
        Optional<CandidateEntity> optionalCandidate = candidateRepository.findById(Long.valueOf(Integer.valueOf(candidateId)));
        if (optionalCandidate.isPresent()) {
            return convertEntityToDTO(optionalCandidate.get());
        } else {
            throw new UserNotFoundException("User not found with id " + candidateId);
        }
    }

    // Get all candidates
    public List<CandidateDTO> getAllCandidates() {
        List<CandidateEntity> candidateList = candidateRepository.findAll();
        List<CandidateDTO> candidateDTOList = new ArrayList<>();
        for (CandidateEntity candidateEntity : candidateList) {
            candidateDTOList.add(convertEntityToDTO(candidateEntity));
        }
        return candidateDTOList;
    }

    // Update candidate
    public CandidateDTO updateCandidate(String candidateId, CandidateDTO candidateDTO) throws UserNotFoundException {
        Optional<CandidateEntity> optionalCandidate = candidateRepository.findById(Long.valueOf(Integer.valueOf(candidateId)));
        if (optionalCandidate.isPresent()) {
            CandidateEntity candidateEntity = optionalCandidate.get();
            BeanUtils.copyProperties(candidateDTO, candidateEntity, "candidateId");
            candidateRepository.save(candidateEntity);
            return convertEntityToDTO(candidateEntity);
        } else {
            throw new UserNotFoundException("User not found with id " + candidateId);
        }
    }


    // Delete candidate
    public void deleteCandidate(String candidateId) throws UserNotFoundException {
        Optional<CandidateEntity> optionalCandidate = candidateRepository.findById(Long.valueOf(Integer.valueOf(candidateId)));
        if (optionalCandidate.isPresent()) {
            candidateRepository.deleteById(Long.valueOf(Integer.valueOf(candidateId)));
        } else {
            throw new UserNotFoundException("User not found with id " + candidateId);
        }
    }
}



