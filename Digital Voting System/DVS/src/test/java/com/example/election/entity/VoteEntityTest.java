package com.example.election.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoteEntityTest {
    @Test
    public void testVote(){
        VoterEntity voter=new VoterEntity();
        voter.setVoterId(1);

        ElectionEntity election=new ElectionEntity();
        election.setElectionId(1);

        CandidateEntity candidate=new CandidateEntity();
        candidate.setCandidateId(1);

        VoteEntity vote=new VoteEntity();
        vote.setVoteId(1);
        vote.setVoterEntity(voter);
        vote.setElectionEntity(election);
        vote.setCandidateEntity(candidate);


        assertEquals(1, vote.getVoteId());
        assertEquals(voter, vote.getVoterEntity());
        assertEquals(election, vote.getElectionEntity());
        assertEquals(candidate, vote.getCandidateEntity());

    }
}
