package com.example.election.dto;

import com.example.election.entity.AdminEntity;
import com.example.election.entity.VoterEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private long notificationId;
    @NotNull(message = "voter entity field should not be blank")
    private VoterEntity voterEntity;
    @NotNull(message = "admin entity field should not be blank")
    private AdminEntity adminEntity;
    @NotNull(message = "message field should not be blank")
    private String message;
    @NotNull(message = "date field should not be blank")
    private LocalDateTime date;
}
