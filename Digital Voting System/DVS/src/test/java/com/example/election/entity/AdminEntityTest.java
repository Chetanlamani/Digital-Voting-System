package com.example.election.entity;

import com.example.election.resource.Role;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class AdminEntityTest {

    @Test
    public void testAdminEntity() {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(1);
        userEntity.setRole(Role.ADMIN);

        AdminEntity detail=new AdminEntity();
        detail.setAdminId(1);
        detail.setUserEntity(userEntity);
        detail.setAdminName("deva");
        detail.setEmail("deva34@gmail.com");
        detail.setAge(19);
        detail.setPhoneNumber("6758635463");

        assertEquals(1,detail.getAdminId());
        assertEquals(userEntity,detail.getUserEntity());
        assertEquals("deva", detail.getAdminName());
        assertEquals("deva34@gmail.com", detail.getEmail());
        assertEquals(19, detail.getAge());
        assertEquals("6758635463",detail.getPhoneNumber());
    }
}
