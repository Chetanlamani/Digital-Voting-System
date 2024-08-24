package com.example.election.service;

import com.example.election.dto.AdminDTO;
import com.example.election.entity.AdminEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.AdminRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    // Convert entity to DTO
    AdminDTO convertEntityToDTO(AdminEntity adminEntity) {
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(adminEntity, adminDTO);
        return adminDTO;
    }

    // Convert DTO to entity
    AdminEntity convertDTOToEntity(AdminDTO adminDTO) {
        AdminEntity adminEntity = new AdminEntity();
        BeanUtils.copyProperties(adminDTO, adminEntity);
        return adminEntity;
    }

    // Register user
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        AdminEntity adminEntity = convertDTOToEntity(adminDTO);
        adminRepository.save(adminEntity);
        return convertEntityToDTO(adminEntity);
    }

    // Get all users
    public List<AdminDTO> getAllAdmin() {
        List<AdminEntity> adminEntity_list = adminRepository.findAll();
        List<AdminDTO> adminDTO_list = new ArrayList<>();
        for (AdminEntity adminEntity : adminEntity_list) {
            adminDTO_list.add(convertEntityToDTO(adminEntity));
        }
        return adminDTO_list;
    }
    //
    // Update user
    public AdminDTO updateAdmin(String adminId, AdminDTO adminDTO) throws UserNotFoundException {
        Optional<AdminEntity> optionalAdmin = adminRepository.findById(Long.valueOf(Integer.valueOf(adminId)));
        if (optionalAdmin.isPresent()) {
            AdminEntity adminEntity = optionalAdmin.get();
            BeanUtils.copyProperties(adminDTO, adminEntity, "adminId");
            adminRepository.save(adminEntity);
            return convertEntityToDTO(adminEntity);
        } else {
            throw new UserNotFoundException("User not found with id " + adminId);
        }
    }

    // Delete user
    public void deleteAdmin(String adminId) throws UserNotFoundException {
        Optional<AdminEntity> optionalAdmin = adminRepository.findById(Long.valueOf(Integer.valueOf(adminId)));
        if (optionalAdmin.isPresent()) {
            adminRepository.deleteById(Long.valueOf(Integer.valueOf(adminId)));
        } else {
            throw new UserNotFoundException("User not found with id " + adminId);
        }
    }

    // Get user by ID
    public AdminDTO getAdminById(String adminId) throws UserNotFoundException {
        Optional<AdminEntity> optionalAdmin = adminRepository.findById(Long.valueOf(Integer.valueOf(adminId)));
        if (optionalAdmin.isPresent()) {
            return convertEntityToDTO(optionalAdmin.get());
        } else {
            throw new UserNotFoundException("User not found with id " + adminId);
        }
    }

}

