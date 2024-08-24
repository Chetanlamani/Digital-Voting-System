package com.example.election.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationEntityTest {
    @Test
    public void testNotification(){
        VoterEntity voter=new VoterEntity();
        voter.setVoterId(1);

        AdminEntity admin=new AdminEntity();
        admin.setAdminId(1);


        NotificationEntity notification=new NotificationEntity();
        notification.setNotificationId(1);
        notification.setVoterEntity(voter);
        notification.setAdminEntity(admin);
        notification.setMessage("voted");

        assertEquals(1, notification.getNotificationId());
        assertEquals(voter, notification.getVoterEntity());
        assertEquals(admin, notification.getAdminEntity());
        assertEquals("voted", notification.getMessage());

    }
}
