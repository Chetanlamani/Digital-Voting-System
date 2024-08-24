package com.example.election.dto;


import com.example.election.entity.UserEntity;
import com.example.election.resource.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CandidateDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void TestCandidateDTOValid() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.ADMIN);

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateId(1);
        candidateDTO.setUserEntity(userEntity);
        candidateDTO.setCandidateName("praveen");
        candidateDTO.setManifesto("Election for all");
        candidateDTO.setParty("Janata");
        candidateDTO.setEmail("praveen34@gmail.com");
        candidateDTO.setAge(18);


        Set<ConstraintViolation<CandidateDTO>> violations = validator.validate(candidateDTO);
        assertTrue(violations.isEmpty(), "there should be a no validation errors");
    }

    @Test
    public void TestCandidateDTOInValid() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateId(1);
        candidateDTO.setUserEntity(null);
        candidateDTO.setCandidateName("");
        candidateDTO.setManifesto("");
        candidateDTO.setParty("");
        candidateDTO.setEmail("null");
        candidateDTO.setAge(18);



        Set<ConstraintViolation<CandidateDTO>> violations = validator.validate(candidateDTO);
        assertFalse(violations.isEmpty(), "there should be validation errors");

        for (ConstraintViolation<CandidateDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(5, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));

    }
}

