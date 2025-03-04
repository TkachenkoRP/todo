package com.my.todo.service.impl;

import com.my.todo.exception.EntityNotFoundException;
import com.my.todo.model.RoleType;
import com.my.todo.model.User;
import com.my.todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>(Collections.singletonList(RoleType.ROLE_USER)));
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> users = userService.findAll();
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void testFindById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void testFindById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testSave() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);
        assertEquals(user, savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void testUpdate() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.update(user);
        assertEquals(user, updatedUser);
    }

    @Test
    void testUpdate_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void testDeleteById() {
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByEmail_UserExists() {
        when(userRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmail("test@example.com");
        assertEquals(user, foundUser);
    }

    @Test
    void testFindByEmail_UserNotFound() {
        when(userRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail("test@example.com"));
    }

    @Test
    void testAddAdminRole() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.addAdminRole(1L);
        assertTrue(updatedUser.getRoles().contains(RoleType.ROLE_ADMIN));
    }

    @Test
    void testAddAdminRole_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.addAdminRole(1L));
    }

    @Test
    void testRemoveAdminRole() {
        user.getRoles().add(RoleType.ROLE_ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.removeAdminRole(1L);
        assertFalse(updatedUser.getRoles().contains(RoleType.ROLE_ADMIN));
    }

    @Test
    void testRemoveAdminRole_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.removeAdminRole(1L));
    }
}