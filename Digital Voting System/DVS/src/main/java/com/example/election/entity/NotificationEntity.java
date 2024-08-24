package com.example.election.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_details")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;
    @ManyToOne
    @JoinColumn(name = "voterId",referencedColumnName = "voterId")
    private VoterEntity voterEntity;
    @ManyToOne
    @JoinColumn(name = "adminId",referencedColumnName = "adminId")
    private AdminEntity adminEntity;
    private String message;
    private LocalDateTime date;
}
