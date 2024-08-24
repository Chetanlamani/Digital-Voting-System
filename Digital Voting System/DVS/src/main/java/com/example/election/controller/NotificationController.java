package com.example.election.controller;

import com.example.election.dto.NotificationDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    @GetMapping("getNotificationById/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(notificationService.getNotificationById(String.valueOf(id)));
    }

    @GetMapping("/getAllNotification")
    public ResponseEntity<List<NotificationDTO>> getAllNotification() {
        try {
            List<NotificationDTO> tokens = notificationService.getAllNotification();
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody @Valid NotificationDTO notificationDTO) {
        try {
            NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification) ;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable long id, @RequestBody @Valid NotificationDTO notificationDTO) throws UserNotFoundException {

        NotificationDTO updatedNotification = notificationService.updateNotification(String.valueOf(id), notificationDTO);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable long id) throws UserNotFoundException {
        notificationService.deleteNotification(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getNotificationByVoterId/{voterId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@PathVariable long voterId) {
        try {
            List<NotificationDTO> notifications = notificationService.getAllNotificationsForVoter(voterId);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

