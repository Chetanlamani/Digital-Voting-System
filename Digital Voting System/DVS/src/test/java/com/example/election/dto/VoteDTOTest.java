package com.example.election.dto;

import com.example.election.entity.CandidateEntity;
import com.example.election.entity.ElectionEntity;
import com.example.election.entity.VoterEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VoteDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVoteDTOValid() {
        VoterEntity voterEntity = new VoterEntity();
        // Assume VoterEntity has appropriate setters or a constructor
        voterEntity.setVoterId(1);
        voterEntity.setVoterName("John Doe");

        ElectionEntity electionEntity = new ElectionEntity();
        // Assume ElectionEntity has appropriate setters or a constructor
        electionEntity.setElectionId(1);
        electionEntity.setName("Presidential Election");

        CandidateEntity candidateEntity = new CandidateEntity();
        // Assume CandidateEntity has appropriate setters or a constructor
        candidateEntity.setCandidateId(1);
        candidateEntity.setCandidateName("Jane Doe");

        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(1);
        voteDTO.setVoterEntity(voterEntity);
        voteDTO.setElectionEntity(electionEntity);
        voteDTO.setCandidateEntity(candidateEntity);
        voteDTO.setTimestamp(LocalDateTime.now());

        Set<ConstraintViolation<VoteDTO>> violations = validator.validate(voteDTO);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testVoteDTOInvalid() {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(1);
        voteDTO.setVoterEntity(null); // Invalid: null
        voteDTO.setElectionEntity(null); // Invalid: null
        voteDTO.setCandidateEntity(null); // Invalid: null
        voteDTO.setTimestamp(null); // Invalid: null

        Set<ConstraintViolation<VoteDTO>> violations = validator.validate(voteDTO);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<VoteDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
    }
}
