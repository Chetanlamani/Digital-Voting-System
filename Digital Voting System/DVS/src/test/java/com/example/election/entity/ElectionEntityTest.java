package com.example.election.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectionEntityTest {
    @Test
    public void testElectionEntity(){

        AdminEntity admin=new AdminEntity();
        admin.setAdminId(1);

        ElectionEntity election = new ElectionEntity();
        election.setElectionId(1);
        election.setName("mi");
        election.setAdminEntity(admin);
        election.setStatus("ongoing");


        assertEquals(1, election.getElectionId());
        assertEquals(admin, election.getAdminEntity());
        assertEquals("mi", election.getName());
        assertEquals("ongoing", election.getStatus());


    }
}
