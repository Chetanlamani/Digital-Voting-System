package com.example.election.dto;

import com.example.election.entity.AdminEntity;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ElectionDTOTest {
    private Validator validator;
    @BeforeEach
    public void setUp(){
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        validator=factory.getValidator();
    }

    @Test
    public void TestElectionDTOValid(){
        AdminEntity adminEntity=new AdminEntity();
        adminEntity.setAdminId(1);
        adminEntity.setAdminName("suresh");

        ElectionDTO electionDTO=new ElectionDTO();
        electionDTO.setElectionId(1);
        electionDTO.setName("MLA");
        electionDTO.setStartDate(LocalDateTime.now());
        electionDTO.setEndDate(LocalDateTime.now());
        electionDTO.setAdminEntity(adminEntity);
        electionDTO.setDescription("2002 election");
        electionDTO.setStatus("ongoing");
        Set<ConstraintViolation<ElectionDTO>> violations= validator.validate(electionDTO);
        assertTrue(violations.isEmpty(),"there should not  be a validation errors");

    }
    @Test
    public void TestElectionDTOInValid(){
        AdminEntity adminEntity=new AdminEntity();
        adminEntity.setAdminId(1);
        adminEntity.setAdminName("suresh");

        ElectionDTO electionDTO=new ElectionDTO();
        electionDTO.setElectionId(1);
        electionDTO.setName("");
        electionDTO.setStartDate(null);
        electionDTO.setEndDate(null);
        electionDTO.setAdminEntity(null);
        electionDTO.setDescription("");
        electionDTO.setStatus("");
        Set<ConstraintViolation<ElectionDTO>> violations= validator.validate(electionDTO);
        assertFalse(violations.isEmpty(),"there should  be a validation errors");

        for (ConstraintViolation<ElectionDTO> violation:violations){
            System.out.println(violation.getMessage());
        }
        assertEquals(6,violations.size());
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));
        assertFalse(violations.stream().anyMatch(v->v.getMessage().equals(" ")));

    }

}
