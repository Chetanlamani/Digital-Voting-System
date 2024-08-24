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
@Table(name = "vote_details")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voteId;
    @OneToOne
    @JoinColumn(name = "voterId",referencedColumnName = "voterId")
    private VoterEntity voterEntity;
    @ManyToOne
    @JoinColumn(name = "electionId",referencedColumnName = "electionId")
    private ElectionEntity electionEntity;
    @ManyToOne
    @JoinColumn(name = "candidateId",referencedColumnName = "candidateId")
    private CandidateEntity candidateEntity;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
}
