package com.example.election.repository;

import com.example.election.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    List<NotificationEntity>findByVoterEntity_voterId(long voterId);
}
