package com.example.election.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voter_details")
public class  VoterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  voterId;
    private String voterName;
    private String email;
    private String dateOfBirth;
    private int age;
    private String address;
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private UserEntity userEntity;

}
