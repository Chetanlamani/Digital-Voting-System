package com.example.election.repository;

import com.example.election.entity.NotificationEntity;
import com.example.election.entity.VoterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class NotificationRepositoryTest {

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationEntity notificationEntity;
    private VoterEntity voterEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        voterEntity = new VoterEntity();
        voterEntity.setVoterId(1L);
        voterEntity.setVoterName("John Doe");

        notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(1L);
        notificationEntity.setMessage("Election Notification");
        notificationEntity.setVoterEntity(voterEntity);
    }

    @Test
    public void testSaveNotification() {
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);

        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);

        assertNotNull(savedNotification);
        assertEquals(notificationEntity.getNotificationId(), savedNotification.getNotificationId());
    }

    @Test
    public void testFindByIdSuccess() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notificationEntity));

        Optional<NotificationEntity> foundNotification = notificationRepository.findById(1L);

        assertTrue(foundNotification.isPresent());
        assertEquals(notificationEntity.getNotificationId(), foundNotification.get().getNotificationId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(notificationRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<NotificationEntity> foundNotification = notificationRepository.findById(2L);

        assertFalse(foundNotification.isPresent());
    }

    @Test
    public void testFindByVoterEntity_voterIdSuccess() {
        List<NotificationEntity> notifications = new ArrayList<>();
        notifications.add(notificationEntity);

        when(notificationRepository.findByVoterEntity_voterId(1L)).thenReturn(notifications);

        List<NotificationEntity> foundNotifications = notificationRepository.findByVoterEntity_voterId(1L);

        assertNotNull(foundNotifications);
        assertFalse(foundNotifications.isEmpty());
        assertEquals(1, foundNotifications.size());
        assertEquals(notificationEntity.getNotificationId(), foundNotifications.get(0).getNotificationId());
    }

    @Test
    public void testFindByVoterEntity_voterIdNotFound() {
        when(notificationRepository.findByVoterEntity_voterId(2L)).thenReturn(new ArrayList<>());

        List<NotificationEntity> foundNotifications = notificationRepository.findByVoterEntity_voterId(2L);

        assertNotNull(foundNotifications);
        assertTrue(foundNotifications.isEmpty());
    }

    @Test
    public void testDeleteNotification() {
        doNothing().when(notificationRepository).delete(notificationEntity);

        notificationRepository.delete(notificationEntity);

        verify(notificationRepository, times(1)).delete(notificationEntity);
    }
}
