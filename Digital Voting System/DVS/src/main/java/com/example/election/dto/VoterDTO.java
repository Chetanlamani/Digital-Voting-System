package com.example.election.dto;

import com.example.election.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VoterDTO {
    private long voterId;
    @NotBlank(message = "voter name field should not be blank")
    private String voterName;
    @Email(message = "email field should not be blank")
    private String email;
    @NotNull(message = " date Of Birth field should not be blank")
    private String dateOfBirth;
    @NotNull(message = "age field should not be blank")
    private int age;
    @NotBlank(message = "address field should not be blank")
    private String address;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number is entered")
    private String phoneNumber;
    @NotNull(message = "user entity field should not be blank")
    private UserEntity userEntity;

}
