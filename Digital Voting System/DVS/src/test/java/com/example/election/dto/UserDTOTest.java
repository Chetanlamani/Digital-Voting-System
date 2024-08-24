package com.example.election.dto;

import com.example.election.resource.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserDTOValid() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUsername("JohnDoe");
        userDTO.setPassword("password");
        userDTO.setRole(Role.VOTER);
        userDTO.setRole(Role.CANDIDATE);
        userDTO.setRole(Role.ADMIN);


        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testUserDTOInvalid() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUsername("");
        userDTO.setPassword("");
        userDTO.setRole(null);
        userDTO.setRole(null);
        userDTO.setRole(null);

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<UserDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(3, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));


    }
}
