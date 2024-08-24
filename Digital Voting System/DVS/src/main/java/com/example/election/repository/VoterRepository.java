package com.example.election.repository;

import com.example.election.entity.VoterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<VoterEntity,Long> {
}
