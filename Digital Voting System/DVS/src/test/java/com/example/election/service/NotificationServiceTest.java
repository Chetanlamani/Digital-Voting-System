package com.example.election.service;

import com.example.election.dto.NotificationDTO;
import com.example.election.entity.NotificationEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.NotificationRepository;
import com.example.election.repository.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private VoterRepository voterRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setMessage("New notification");

        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notificationDTO, notificationEntity);

        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);

        NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);

        assertNotNull(createdNotification);
        assertEquals(notificationDTO.getMessage(), createdNotification.getMessage());
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testGetNotificationById_Success() throws UserNotFoundException {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(1);
        notificationEntity.setMessage("Test notification");

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notificationEntity));

        NotificationDTO notificationDTO = notificationService.getNotificationById("1");

        assertNotNull(notificationDTO);
        assertEquals(notificationEntity.getMessage(), notificationDTO.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNotificationById_UserNotFoundException() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.getNotificationById("1"));
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllNotificationsForUser() {
        List<NotificationEntity> notificationEntities = new ArrayList<>();
        NotificationEntity notificationEntity1 = new NotificationEntity();
        notificationEntity1.setMessage("Notification 1");
        NotificationEntity notificationEntity2 = new NotificationEntity();
        notificationEntity2.setMessage("Notification 2");
        notificationEntities.add(notificationEntity1);
        notificationEntities.add(notificationEntity2);

        when(notificationRepository.findByVoterEntity_voterId(1)).thenReturn(notificationEntities);

        List<NotificationDTO> notificationDTOList = notificationService.getAllNotificationsForVoter(1);

        assertNotNull(notificationDTOList);
        assertEquals(2, notificationDTOList.size());
        verify(notificationRepository, times(1)).findByVoterEntity_voterId(1);
    }

    @Test
    void testGetAllNotifications() {
        List<NotificationEntity> notificationEntities = new ArrayList<>();
        NotificationEntity notificationEntity1 = new NotificationEntity();
        notificationEntity1.setMessage("Notification 1");
        NotificationEntity notificationEntity2 = new NotificationEntity();
        notificationEntity2.setMessage("Notification 2");
        notificationEntities.add(notificationEntity1);
        notificationEntities.add(notificationEntity2);

        when(notificationRepository.findAll()).thenReturn(notificationEntities);

        List<NotificationDTO> notificationDTOList = notificationService.getAllNotification();

        assertNotNull(notificationDTOList);
        assertEquals(2, notificationDTOList.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testUpdateNotification_Success() throws UserNotFoundException {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(1);
        notificationEntity.setMessage("Old notification");

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setMessage("Updated notification");

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notificationEntity));
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);

        NotificationDTO updatedNotification = notificationService.updateNotification("1", notificationDTO);

        assertNotNull(updatedNotification);
        assertEquals(notificationDTO.getMessage(), updatedNotification.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testUpdateNotification_UserNotFoundException() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setMessage("Updated notification");

        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.updateNotification("1", notificationDTO));
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteNotification_Success() throws UserNotFoundException {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(1);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notificationEntity));
        doNothing().when(notificationRepository).deleteById(1L);

        notificationService.deleteNotification("1");

        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotification_UserNotFoundException() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.deleteNotification("1"));
        verify(notificationRepository, times(1)).findById(1L);
    }
}
