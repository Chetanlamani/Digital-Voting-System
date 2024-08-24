package com.example.election.dto;

import com.example.election.entity.CandidateEntity;
import com.example.election.entity.ElectionEntity;
import com.example.election.entity.VoterEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class VoteDTO {
    private long voteId;
    @NotNull(message = "voter entity field should not be blank")
    private VoterEntity voterEntity;
    @NotNull(message = "election entity field should not be blank")
    private ElectionEntity electionEntity;
    @NotNull(message = "candidate entity field should not be blank")
    private CandidateEntity candidateEntity;
    @NotNull(message = "date field should not be blank")
    private LocalDateTime timestamp;
}
