package com.example.election.entity;

import com.example.election.resource.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandidateEntityTest {
    @Test
    public void testCandidateEntity(){

        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.CANDIDATE);

        CandidateEntity candidate = new CandidateEntity();
        candidate.setCandidateId(1);
        candidate.setUserEntity(userEntity);
        candidate.setCandidateName("umesh");
        candidate.setParty("UDS");
        candidate.setManifesto("election");
        candidate.setAge(26);
        candidate.setPhoneNumber("6665554442");

        assertEquals(1, candidate.getCandidateId());
        assertEquals(userEntity, candidate.getUserEntity());
        assertEquals("umesh", candidate.getCandidateName());
        assertEquals("UDS", candidate.getParty());
        assertEquals("election", candidate.getManifesto());
        assertEquals(26, candidate.getAge());
        assertEquals("6665554442", candidate.getPhoneNumber());

    }
}




