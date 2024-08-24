package com.example.election.dto;

import com.example.election.resource.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Role role;
}
