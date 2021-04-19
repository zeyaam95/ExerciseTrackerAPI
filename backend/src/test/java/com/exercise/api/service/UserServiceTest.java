package com.exercise.api.service;

import com.exercise.api.model.User;
import com.exercise.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(Mockito.mock(User.class));
        users.add(Mockito.mock(User.class));
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(result, users);
    }

    @Test
    void getUserById() throws Exception {
        User user = Mockito.mock(User.class);
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        User result = userService.getUserById(0);
        assertEquals(user, result);
    }

    @Test
    void getUserById_shouldThrowException() {
        assertThrows(Exception.class, ()-> userService.getUserById(-3));
    }

    @Test
    void getUserByUserName() {
        User user = Mockito.mock(User.class);
        when(userRepository.findUserByUserName(anyString())).thenReturn(user);
        User result = userService.userRepository.findUserByUserName("LarryWheels");
        assertEquals(user, result);
    }

    @Test
    void getUserByUserName_shouldThrowException() {
        assertThrows(Exception.class, ()-> userService.getUserByUserName(""));
    }

    @Test
    void saveOrUpdate() throws Exception {
        User user = Mockito.mock(User.class);
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.saveOrUpdate(user);
        verify(userRepository).save(user);
    }

    @Test
    void saveOrUpdate_shouldThrowException() {
        User user = new User(-3, "testName", "12345", Mockito.mock(Date.class), 100, 100, Mockito.mock(Date.class));
        assertThrows(Exception.class, () -> userService.saveOrUpdate(user));
    }

    @Test
    void delete() throws Exception {
        User user = Mockito.mock(User.class);
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        userService.delete(user.getUserId());
        verify(userRepository).deleteById(user.getUserId());
    }

    @Test
    void delete_shouldThrowException() {
        assertThrows(Exception.class, ()-> userService.delete(-3));
    }
}