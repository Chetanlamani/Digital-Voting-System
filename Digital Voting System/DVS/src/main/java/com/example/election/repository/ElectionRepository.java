package com.example.election.repository;

import com.example.election.entity.ElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<ElectionEntity, Long> {
}
