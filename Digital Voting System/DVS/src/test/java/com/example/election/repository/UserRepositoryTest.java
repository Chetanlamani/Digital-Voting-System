package com.example.election.repository;

import com.example.election.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setUserId(1);
        user.setUsername("sharat");
        user.setPassword("password");
    }

    @Test
    public void testFindByUsernameSuccess() {
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        Optional<UserEntity> foundUser = userRepository.findByUsername("sharat");
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
        Optional<UserEntity> foundUser = userRepository.findByUsername("nonexistentuser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testSaveUser() {
        UserEntity newUser = new UserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);
        newUser.setUserId(1);
        newUser.setUsername("xyz");
        newUser.setPassword("password123");
        UserEntity savedUser = userRepository.save(newUser);
        assertNotNull(savedUser.getUserId());
        assertEquals("password123", savedUser.getPassword());
        assertEquals("xyz", savedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}
