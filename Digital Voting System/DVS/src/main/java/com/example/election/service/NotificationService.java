package com.example.election.service;

import com.example.election.dto.NotificationDTO;
import com.example.election.entity.NotificationEntity;
import com.example.election.exception.UserNotFoundException;
import com.example.election.repository.NotificationRepository;
import com.example.election.repository.VoterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private VoterRepository voterRepository;

    // Convert entity to DTO
    private NotificationDTO convertEntityToDTO(NotificationEntity notificationEntity) {
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notificationEntity, notificationDTO);

        return notificationDTO;
    }

    // Convert DTO to entity
    private NotificationEntity convertDTOToEntity(NotificationDTO notificationDTO) {
        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notificationDTO, notificationEntity);
        return notificationEntity;
    }

    // Create notification
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        NotificationEntity notificationEntity = convertDTOToEntity(notificationDTO);
        notificationRepository.save(notificationEntity);
        return convertEntityToDTO(notificationEntity);
    }

    // Get notification by ID
    public NotificationDTO getNotificationById(String notificationId) throws UserNotFoundException {
        Optional<NotificationEntity> optionalNotification = notificationRepository.findById(Long.valueOf(Integer.valueOf(notificationId)));
        NotificationEntity notificationEntity = optionalNotification.orElseThrow(() -> new UserNotFoundException("Notification not found with id " + notificationId));
        return convertEntityToDTO(notificationEntity);
    }

    // Get all notifications for a voter
    public List<NotificationDTO> getAllNotificationsForVoter(long voterId) {
        List<NotificationEntity> notificationList = notificationRepository.findByVoterEntity_voterId(voterId);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (NotificationEntity notification : notificationList) {
            notificationDTOList.add(convertEntityToDTO(notification));
        }
        return notificationDTOList;
    }

    public List<NotificationDTO>getAllNotification(){
        List<NotificationEntity>notificationList=notificationRepository.findAll();
        List<NotificationDTO>notificationDTOList=new ArrayList<>();
        for(NotificationEntity notification:notificationList){
            notificationDTOList.add(convertEntityToDTO(notification));
        }
        return notificationDTOList;
    }

    // Update notification
    public NotificationDTO updateNotification(String notificationId, NotificationDTO notificationDTO) throws UserNotFoundException {
        Optional<NotificationEntity> optionalNotification = notificationRepository.findById(Long.valueOf(Integer.valueOf(notificationId)));
        NotificationEntity notificationEntity = optionalNotification.orElseThrow(() -> new UserNotFoundException("Notification not found with id " + notificationId));
        BeanUtils.copyProperties(notificationDTO, notificationEntity, "notificationId");
        notificationRepository.save(notificationEntity);
        return convertEntityToDTO(notificationEntity);
    }

    // Delete notification
    public void deleteNotification(String notificationId) throws UserNotFoundException {
        Optional<NotificationEntity> optionalNotification = notificationRepository.findById(Long.valueOf(Integer.valueOf(notificationId)));
        if (optionalNotification.isPresent()) {
            notificationRepository.deleteById(Long.valueOf(Integer.valueOf(notificationId)));
        } else {
            throw new UserNotFoundException("Notification not found with id " + notificationId);
        }
    }
}
