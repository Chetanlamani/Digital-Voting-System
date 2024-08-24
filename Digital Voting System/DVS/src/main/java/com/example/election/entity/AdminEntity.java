package com.example.election.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin_details")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;
    private String adminName;
    private String email;
    private int age;
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private UserEntity userEntity;
}
