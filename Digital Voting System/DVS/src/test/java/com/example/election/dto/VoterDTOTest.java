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

public class VoterDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVoterDTOValid() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.ADMIN);

        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setVoterId(1);
        voterDTO.setUserEntity(userEntity);
        voterDTO.setVoterName("JohnDoe");
        voterDTO.setEmail("john.doe@example.com");
        voterDTO.setDateOfBirth("1990-01-01");
        voterDTO.setAddress("123 Main St");
        voterDTO.setPhoneNumber("1234567890");
        voterDTO.setAge(18);


        Set<ConstraintViolation<VoterDTO>> violations = validator.validate(voterDTO);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testVoterDTOInvalid() {
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setVoterId(1);
        voterDTO.setUserEntity(null);
        voterDTO.setVoterName("");
        voterDTO.setEmail("");
        voterDTO.setDateOfBirth(null);
        voterDTO.setAddress("");
        voterDTO.setPhoneNumber("123");

        Set<ConstraintViolation<VoterDTO>> violations = validator.validate(voterDTO);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<VoterDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(5, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));




    }
}
