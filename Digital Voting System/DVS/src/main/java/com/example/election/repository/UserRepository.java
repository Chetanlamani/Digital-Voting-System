package com.example.election.repository;

import com.example.election.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);
}
