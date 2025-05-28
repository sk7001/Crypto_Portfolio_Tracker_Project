package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.CryptoTrackerApplication.dto.UserRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.CryptoTrackerApplication.dto.UserResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.Mappers.UserMapper;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private  UserService userService;

    // Create new user
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    // Get all users
    @GetMapping("/getallusers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    
    //login
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public UserResponseDTO login(@RequestBody UserAuthDTO authDTO) {
        return userService.login(authDTO);
    }

    // Get user by email
    @GetMapping("/email")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return UserMapper.EntityTodto(userService.getbyEmail(email));
    }

    //  Update only role
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateUserRole(id, role);
    }


    // ✅ Delete user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
       
    }
}

