package com.example.election.entity;

import com.example.election.resource.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoterEntityTest {
    @Test
    public  void  testVoterEntity(){
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.VOTER);

        VoterEntity voter=new VoterEntity();
        voter.setVoterId(1);
        voter.setUserEntity(userEntity);
        voter.setVoterName("umesh");
        voter.setPhoneNumber("45325678");
        voter.setEmail("umesh@gmail.com");
        voter.setAge(20);

        assertEquals(1, voter.getVoterId());
        assertEquals(userEntity, voter.getUserEntity());
        assertEquals("umesh", voter.getVoterName());
        assertEquals("45325678", voter.getPhoneNumber());
        assertEquals("umesh@gmail.com", voter.getEmail());
        assertEquals(20, voter.getAge());



    }
}
