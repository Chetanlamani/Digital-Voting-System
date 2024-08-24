package com.example.election.dto;

import com.example.election.entity.AdminEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ElectionDTO {
    private long electionId;
    @NotBlank(message = "name field should not be blank")
    private String name;
    @NotBlank(message = "description field should not be blank")
    private String description;
    @NotNull(message = "start date field should not be blank")
    private LocalDateTime startDate;
    @NotNull(message = "end date field should not be blank")
    private LocalDateTime endDate;
    @NotBlank(message = "status field should not be blank")
    private String status;
    @NotNull(message = "admin entity  field should not be blank")
    private AdminEntity adminEntity;
}
