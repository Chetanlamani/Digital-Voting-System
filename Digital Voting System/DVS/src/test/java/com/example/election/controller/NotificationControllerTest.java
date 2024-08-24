package com.example.election.controller;

import com.example.election.dto.NotificationDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    @Mock
    private  NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNotificationSuccess() {
        NotificationDTO dto = new NotificationDTO();
        NotificationDTO createdDto = new NotificationDTO();

        when(notificationService.createNotification(any(NotificationDTO.class))).thenReturn(createdDto);

        ResponseEntity<NotificationDTO> response = notificationController.createNotification(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllNotificationsSuccess() {
        NotificationDTO dto1 = new NotificationDTO();
        NotificationDTO dto2 = new NotificationDTO();
        List<NotificationDTO> votes = Arrays.asList(dto1, dto2);

        when(notificationService.getAllNotification()).thenReturn(votes);

        ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotification();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(votes, response.getBody());
    }

    @Test
    public void testUpdateNotificationSuccess() throws UserNotFoundException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(1);
        NotificationDTO updatedDto = new NotificationDTO();

        when(notificationService.getNotificationById(anyString())).thenReturn(dto);
        when(notificationService.updateNotification(anyString(), any(NotificationDTO.class))).thenReturn(updatedDto);

        ResponseEntity<NotificationDTO> response = notificationController.updateNotification(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotificationNotFound() throws UserNotFoundException {
        NotificationDTO dto = new NotificationDTO();

        when(notificationService.getNotificationById(anyString())).thenThrow(new UserNotFoundException("Notification not found"));

        try {
            notificationController.updateNotification(999, dto);
        } catch (UserNotFoundException e) {
            assertEquals("Notification not found", e.getMessage());
        }
    }


    @Test
    public void testGetNotificationByIdSuccess() throws UserNotFoundException {
        NotificationDTO vote = new NotificationDTO();
        when(notificationService.getNotificationById(anyString())).thenReturn(vote);

        ResponseEntity<NotificationDTO> response = notificationController.getNotificationById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vote, response.getBody());
    }

    @Test
    public void testGetNotificationByIdNotFound() throws UserNotFoundException {
        when(notificationService.getNotificationById(anyString())).thenThrow(new UserNotFoundException("Notification not found"));

        try {
            notificationController.getNotificationById(999);
        } catch (UserNotFoundException e) {
            assertEquals("Notification not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteNotificationSuccess() throws UserNotFoundException {
        doNothing().when(notificationService).deleteNotification(anyString());

        ResponseEntity<Void> response = notificationController.deleteNotification(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteNotificationNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("vote not found")).when(notificationService).deleteNotification(anyString());

        try {
            notificationController.deleteNotification(999);
        } catch (UserNotFoundException e) {
            assertEquals("vote not found", e.getMessage());
        }
    }

    @Test
    public void testGetAllNotificationsByVoterIdSuccess() {
        NotificationDTO dto1 = new NotificationDTO();
        NotificationDTO dto2 = new NotificationDTO();
        List<NotificationDTO> notifications = Arrays.asList(dto1, dto2);

        when(notificationService.getAllNotificationsForVoter(anyLong())).thenReturn(notifications);

        ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotifications(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
    }
}
