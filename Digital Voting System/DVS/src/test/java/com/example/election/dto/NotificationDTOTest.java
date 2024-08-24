package com.example.election.dto;

import com.example.election.entity.AdminEntity;
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

public class NotificationDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNotificationDTOValid() {
        VoterEntity voterEntity = new VoterEntity();
        AdminEntity adminEntity = new AdminEntity();

        // Assume VoterEntity has appropriate setters or a constructor
        voterEntity.setVoterId(1);
        voterEntity.setVoterName("John Doe");

        adminEntity.setAdminId(1);
        adminEntity.setAdminName("joe");

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(1);
        notificationDTO.setVoterEntity(voterEntity);
        notificationDTO.setAdminEntity(adminEntity);
        notificationDTO.setMessage("You have a new message");
        notificationDTO.setDate(LocalDateTime.now());
        

        Set<ConstraintViolation<NotificationDTO>> violations = validator.validate(notificationDTO);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testNotificationDTOInvalid() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(1);
        notificationDTO.setVoterEntity(null);
        notificationDTO.setAdminEntity(null);// Invalid: null
        notificationDTO.setMessage(null); // Invalid: null
        notificationDTO.setDate(null); // Invalid: null
        
        Set<ConstraintViolation<NotificationDTO>> violations = validator.validate(notificationDTO);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<NotificationDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" ")));

    }
}

