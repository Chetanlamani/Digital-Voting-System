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
@Table(name = "election_details")
public class ElectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long electionId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private  LocalDateTime endDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "adminId",referencedColumnName = "adminId")
    private AdminEntity adminEntity;
}
