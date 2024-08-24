package com.example.election.controller;

import com.example.election.dto.*;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private VoterService voterService;

    @Mock
    private CandidateService candidateService;


    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Admin Tests
    @Test
    public void testGetAllAdminsSuccess() {
        AdminDTO admin1 = new AdminDTO();
        AdminDTO admin2 = new AdminDTO();
        List<AdminDTO> admins = Arrays.asList(admin1, admin2);
        when(adminService.getAllAdmin()).thenReturn(admins);

        ResponseEntity<List<AdminDTO>> response = adminController.getAllAdmin();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admins, response.getBody());
    }

    @Test
    public void testGetAdminByIdSuccess() throws UserNotFoundException {
        AdminDTO admin = new AdminDTO();
        when(adminService.getAdminById(anyString())).thenReturn(admin);

        ResponseEntity<AdminDTO> response = adminController.getAdminById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    public void testGetAdminByIdNotFound() throws UserNotFoundException {
        when(adminService.getAdminById(anyString())).thenThrow(new UserNotFoundException("Admin not found"));

        try {
            adminController.getAdminById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Admin not found", e.getMessage());
        }
    }

    @Test
    public void testCreateAdminSuccess() {
        AdminDTO dto = new AdminDTO();
        AdminDTO createdDto = new AdminDTO();

        when(adminService.createAdmin(any(AdminDTO.class))).thenReturn(createdDto);

        ResponseEntity<?> response = adminController.createAdmin(dto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Age must be Greater than or Equal to 18", response.getBody());
    }

    @Test
    public void testUpdateAdminSuccess() throws UserNotFoundException {
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(1);
        AdminDTO updatedDto = new AdminDTO();

        when(adminService.getAdminById(anyString())).thenReturn(dto);
        when(adminService.updateAdmin(anyString(), any(AdminDTO.class))).thenReturn(updatedDto);

        ResponseEntity<AdminDTO> response = adminController.updateAdmin(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateAdminNotFound() throws UserNotFoundException {
        AdminDTO dto = new AdminDTO();

        when(adminService.getAdminById(anyString())).thenThrow(new UserNotFoundException("Admin not found"));

        try {
            adminController.updateAdmin(999, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Admin not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteAdminSuccess() throws UserNotFoundException {
        doNothing().when(adminService).deleteAdmin(anyString());

        ResponseEntity<Void> response = adminController.deleteAdmin(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("admin not found")).when(adminService).deleteAdmin(anyString());

        try {
            adminController.deleteAdmin(999);
        } catch (UserNotFoundException e) {
            assertEquals("admin not found", e.getMessage());
        }
    }


    @Test
    public void testGetAllVoterSuccess() {
        VoterDTO dto1 = new VoterDTO();
        VoterDTO dto2 = new VoterDTO();
        List<VoterDTO>  voters = Arrays.asList(dto1, dto2);

        when(voterService.getAllVoter()).thenReturn( voters);

        ResponseEntity<List<VoterDTO>> response = adminController.getAllVoters();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals( voters, response.getBody());
    }

    @Test
    public void testGetAllCandidatesSuccess() {
        CandidateDTO dto1 = new CandidateDTO();
        CandidateDTO dto2 = new CandidateDTO();
        List<CandidateDTO> candidates = Arrays.asList(dto1, dto2);

        when(candidateService.getAllCandidates()).thenReturn(candidates);

        ResponseEntity<List<CandidateDTO>> response = adminController.getAllCandidate();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(candidates, response.getBody());
    }


}
