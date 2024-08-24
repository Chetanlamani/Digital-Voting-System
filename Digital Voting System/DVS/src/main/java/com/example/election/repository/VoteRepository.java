package com.example.election.repository;

import com.example.election.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    List<VoteEntity> findByVoterEntity_voterId(long voterId);
    List<VoteEntity> findByCandidateEntity_candidateId(long candidateId);

}
