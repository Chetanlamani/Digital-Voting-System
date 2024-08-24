package com.example.election.service;

import com.example.election.dto.AdminDTO;
import com.example.election.entity.AdminEntity;
import com.example.election.entity.UserEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.AdminRepository;
import com.example.election.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAdmin() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.ADMIN);
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        adminDTO.setUserEntity(userEntity);
        adminDTO.setAdminName("John Doe");
        adminDTO.setEmail("john.doe@example.com");
        adminDTO.setAge(19);
        AdminEntity adminEntity = new AdminEntity();
        BeanUtils.copyProperties(adminDTO, adminEntity);

        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);

        AdminDTO savedAdmin = adminService.createAdmin(adminDTO);

        assertNotNull(savedAdmin);
        assertEquals(adminDTO.getAdminId(), savedAdmin.getAdminId());
        assertEquals(adminDTO.getUserEntity(), savedAdmin.getUserEntity());
        assertEquals(adminDTO.getAdminName(), savedAdmin.getAdminName());
        assertEquals(adminDTO.getEmail(), savedAdmin.getEmail());
    }

   

    

    @Test
    public void testGetAllAdmin() {
        List<AdminEntity> adminEntities = new ArrayList<>();
        AdminEntity adminEntity1 = new AdminEntity();
        adminEntity1.setAdminId(1);
        adminEntity1.setAdminName("Admin One");

        AdminEntity adminEntity2 = new AdminEntity();
        adminEntity2.setAdminId(2);
        adminEntity2.setAdminName("Admin Two");

        adminEntities.add(adminEntity1);
        adminEntities.add(adminEntity2);

        when(adminRepository.findAll()).thenReturn(adminEntities);

        List<AdminDTO> adminDTOs = adminService.getAllAdmin();

        assertEquals(2, adminDTOs.size());
        assertEquals("Admin One", adminDTOs.get(0).getAdminName());
        assertEquals("Admin Two", adminDTOs.get(1).getAdminName());
    }

    @Test
    public void testUpdateAdmin() throws UserNotFoundException {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName("Updated Name");

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(1);

        when(adminRepository.findById(1L)).thenReturn(Optional.of(adminEntity));
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);

        AdminDTO updatedAdmin = adminService.updateAdmin("1", adminDTO);

        assertNotNull(updatedAdmin);
        assertEquals(adminDTO.getAdminName(), updatedAdmin.getAdminName());
    }

    @Test
    public void testUpdateAdminNotFound() {
        AdminDTO adminDTO = new AdminDTO();

        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.updateAdmin("1", adminDTO);
        });
    }

    @Test
    public void testDeleteAdmin() throws UserNotFoundException {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(2);

        when(adminRepository.findById(2L)).thenReturn(Optional.of(adminEntity));

        adminService.deleteAdmin("2");

        verify(adminRepository, times(1)).deleteById(2L);
    }

    @Test
    public void testDeleteAdminNotFound() {
        when(adminRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.deleteAdmin("1");
        });
    }

    @Test
    public void testGetAdminById() throws UserNotFoundException {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(1);

        when(adminRepository.findById(1L)).thenReturn(Optional.of(adminEntity));

        AdminDTO adminDTO = adminService.getAdminById("1");

        assertNotNull(adminDTO);
        assertEquals(1, adminDTO.getAdminId());
    }

    @Test
    public void testGetAdminByIdNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.getAdminById("1");
        });
    }
}
