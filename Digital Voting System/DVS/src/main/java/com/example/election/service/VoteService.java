package com.example.election.service;

import com.example.election.dto.VoteDTO;
import com.example.election.entity.VoteEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    // Convert entity to DTO
    private VoteDTO convertEntityToDTO(VoteEntity voteEntity) {
        VoteDTO voteDTO = new VoteDTO();
        BeanUtils.copyProperties(voteEntity, voteDTO);
        return voteDTO;
    }

    // Convert DTO to entity
    private VoteEntity convertDTOToEntity(VoteDTO voteDTO) {
        VoteEntity voteEntity = new VoteEntity();
        BeanUtils.copyProperties(voteDTO, voteEntity);
        return voteEntity;
    }

    // Create vote
    public VoteDTO createVote(VoteDTO voteDTO) {
        VoteEntity voteEntity = convertDTOToEntity(voteDTO);
        voteRepository.save(voteEntity);
        return convertEntityToDTO(voteEntity);
    }

    // Get vote by ID
    public VoteDTO getVoteById(String voteId) throws UserNotFoundException {
        Optional<VoteEntity> optionalVote = voteRepository.findById(Long.valueOf(Integer.valueOf(voteId)));
        if (optionalVote.isPresent()) {
            return convertEntityToDTO(optionalVote.get());
        } else {
            throw new UserNotFoundException("User not found with id " + voteId);
        }
    }

    // Get all vote
    public List<VoteDTO> getAllVote() {
        List<VoteEntity> voteList = voteRepository.findAll();
        List<VoteDTO> voteDTOList = new ArrayList<>();
        for (VoteEntity voteEntity : voteList) {
            voteDTOList.add(convertEntityToDTO(voteEntity));
        }
        return voteDTOList;
    }

    // Update vote
    public VoteDTO updateVote(String voteId, VoteDTO voteDTO) throws UserNotFoundException {
        Optional<VoteEntity> optionalVote = voteRepository.findById(Long.valueOf(Integer.valueOf(voteId)));
        if (optionalVote.isPresent()) {
            VoteEntity voteEntity = optionalVote.get();
            BeanUtils.copyProperties(voteDTO, voteEntity, "voteId");
            voteRepository.save(voteEntity);
            return convertEntityToDTO(voteEntity);
        } else {
            throw new UserNotFoundException("User not found with id " + voteId);
        }
    }


    // Delete vote
    public void deleteVote(String voteId) throws UserNotFoundException {
        Optional<VoteEntity> optionalVote = voteRepository.findById(Long.valueOf(Integer.valueOf(voteId)));
        if (optionalVote.isPresent()) {
            voteRepository.deleteById(Long.valueOf(Integer.valueOf(voteId)));
        } else {
            throw new UserNotFoundException("User not found with id " + voteId);
        }
    }

    // Get all votes for a voter
    public List<VoteDTO> getAllVoteForVoter(long voterId) {
        List<VoteEntity> voteList = voteRepository.findByVoterEntity_voterId(voterId);
        List<VoteDTO> voteDTOList = new ArrayList<>();
        for (VoteEntity vote : voteList) {
            voteDTOList.add(convertEntityToDTO(vote));
        }
        return voteDTOList;
    }


    // Get all votes for a candidate
    public List<VoteDTO> getAllVoteForCandidate(long candidateId) {
        List<VoteEntity> voteList = voteRepository.findByCandidateEntity_candidateId(candidateId);
        List<VoteDTO> voteDTOList = new ArrayList<>();
        for (VoteEntity vote : voteList) {
            voteDTOList.add(convertEntityToDTO(vote));
        }
        return voteDTOList;
    }
}
