package com.example.election.repository;

import com.example.election.entity.AdminEntity;
import com.example.election.entity.UserEntity;
import com.example.election.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminRepositoryTest {

    @Mock
    private AdminRepository adminRepository;

    private AdminEntity adminEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.ADMIN);

        adminEntity = new AdminEntity();
        adminEntity.setAdminId(1L);
        adminEntity.setUserEntity(userEntity);
        adminEntity.setAdminName("John Doe");
        adminEntity.setEmail("john.doe@example.com");
        adminEntity.setAge(30);
        adminEntity.setPhoneNumber("1234567890");
    }

    @Test
    public void testSaveAdmin() {
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);

        AdminEntity savedAdmin = adminRepository.save(adminEntity);

        assertNotNull(savedAdmin);
        assertEquals(adminEntity.getAdminId(), savedAdmin.getAdminId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(adminEntity));

        Optional<AdminEntity> foundAdmin = adminRepository.findById(1L);

        assertTrue(foundAdmin.isPresent());
        assertEquals(adminEntity.getAdminId(), foundAdmin.get().getAdminId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(adminRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<AdminEntity> foundAdmin = adminRepository.findById(2L);

        assertFalse(foundAdmin.isPresent());
    }

    @Test
    public void testDeleteAdmin() {
        doNothing().when(adminRepository).delete(adminEntity);

        adminRepository.delete(adminEntity);

        verify(adminRepository, times(1)).delete(adminEntity);
    }
}
