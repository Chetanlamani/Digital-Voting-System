package com.example.election.dto;

import com.example.election.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateDTO {
    private long  candidateId;
    @NotBlank(message = "name field should not be blank")
    private String candidateName;
    @NotBlank(message = "party field should not be blank")
    private String party;
    @NotBlank(message = "manifesto field should not be blank")
    private String manifesto;
    @NotNull(message = "age field should not be blank")
    private int age;
    @Email(message = "email field should not be blank")
    private String email;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number is entered")
    private String phoneNumber;
    @NotNull(message = "user entity field should not be blank")
    private UserEntity userEntity;
}
