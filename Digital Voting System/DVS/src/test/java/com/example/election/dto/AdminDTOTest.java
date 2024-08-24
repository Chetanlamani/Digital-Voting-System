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



public class AdminDTOTest {
        private Validator validator;

        @BeforeEach
        public void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        public void testAdminDTOValid() {
            UserEntity userEntity=new UserEntity();
            userEntity.setUserId(1);
            userEntity.setRole(Role.ADMIN);

            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setAdminId(1);
            adminDTO.setUserEntity(userEntity);
            adminDTO.setAdminName("John Doe");
            adminDTO.setEmail("john.doe@example.com");
            adminDTO.setPhoneNumber("66464553");



            Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
            assertFalse(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testAdminDTOInvalid() {
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setAdminId(1);
            adminDTO.setUserEntity(null);
            adminDTO.setAdminName(""); // Invalid: blank
            adminDTO.setEmail("null");
            adminDTO.setPhoneNumber(" ");


            Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<AdminDTO> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(4, violations.size());
            assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals(" name  field should not be blank")));
            assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals("email  field should not be blank")));
            assertFalse(violations.stream().anyMatch(v -> v.getMessage().equals("number  field should not be blank")));






        }

}



//@Test
//public void testGetAllNotificationsByVoterSuccess() {
//    NotificationDTO dto1 = new NotificationDTO();
//    NotificationDTO dto2 = new NotificationDTO();
//    List<NotificationDTO> dtos = Arrays.asList(dto1, dto2);
//
//    when(notificationService.getAllNotificationsForVoter(anyInt())).thenReturn(dtos);
//
//    ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotificationsByVoterId(1);
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(dtos, response.getBody());
//}