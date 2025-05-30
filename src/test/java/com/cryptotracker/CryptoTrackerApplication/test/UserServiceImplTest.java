package com.cryptotracker.CryptoTrackerApplication.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.service.UserServiceImpl;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.Mappers.UserMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    //Test case for checking if a user with ADMIN role is correctly identified as an admin.
    @Test
    void testIsAdmin_WhenUserIsAdmin() {
        User admin = new User(1L, "admin@example.com", "Admin", "pass", Role.ADMIN);
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        assertTrue(userService.isAdmin("admin@example.com"));
    }

    //Test case to verify that getAllUser returns a list of UserDTO with correct data mapping.
    @Test
    void testGetAllUser_ReturnsUserDTOList() {
    	User u = new User(1L, "Test", "test@example.com", "password", Role.USER);

        when(userRepository.findAll()).thenReturn(List.of(u));

        List<UserDTO> result = userService.getAllUser();

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        assertEquals("Test", result.get(0).getName());
        assertEquals(Role.USER, result.get(0).getRole());
        assertEquals(1L, result.get(0).getUserId());
    }

     //Test case to verify successful role update of a user by an admin.
    @Test
    void testUpdateUserRole_Success() {
        User admin = new User(1L, "admin@example.com", "Admin", "pass", Role.ADMIN);
        User user = new User(2L, "user@example.com", "User", "pass", Role.USER);

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        boolean result = userService.updateUserRole("admin@example.com", 2L, "admin");

        assertTrue(result);
        assertEquals(Role.ADMIN, user.getRole());
    }

  
    
    //Test case to verify that a user can be fetched correctly by email if present.
    @Test
    void testGetByEmail_Found() {
    	User user = new User(2L, "Test", "test@example.com", "pass", Role.USER);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getbyEmail("test@example.com");

        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals("test@example.com", result.getEmail());
    }
}
