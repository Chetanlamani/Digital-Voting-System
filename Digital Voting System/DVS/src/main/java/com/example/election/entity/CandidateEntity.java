package com.example.election.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate_details")
public class  CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  candidateId;
    private String candidateName;
    private String email;
    private int age;
    private String party;
    private String manifesto;
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private UserEntity userEntity;
}
