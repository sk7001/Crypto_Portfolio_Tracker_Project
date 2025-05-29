package com.cryptotracker.CryptoTrackerApplication.controller;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Validated
public class UserController {
    @Autowired
    private UserServiceImpl service;
    
    //This method is for fetching all the users from the user database only if requesterEmail is admin 
	//api/users
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam String requesterEmail) {
        try {
            if (!service.isAdmin(requesterEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied: only ADMINs can access this endpoint.");
            }

            List<UserDTO> users = service.getAllUser();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

	
	//Here we are checking for the values for validation, the method also validates the attributes below
     //  api/users/1/role/reqPerson?role=ADMIN&reqPerson=adminemail
    @PutMapping("/{id}/role/requesterEmail")
    public ResponseEntity<String> updateUserRole(
    		@PathVariable Long id,
    		@RequestParam @NotBlank(message="role cannot be blank") String role,
    		@RequestParam @NotBlank(message="requesting person's email cannot be blank")
    				      @Email(message="email should be valid") String requesterEmail){
        
    	if(service.updateUserRole(requesterEmail,id,role)) {
    		 return ResponseEntity.ok("User role updated successfully");
    	}else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Role update failed");
    	}       
    }  
    
    
    //Delete the user based on id and email
    //api/users/delete/id/reqPerson?reqPerson=email
    @DeleteMapping("/delete/{id}/reqPerson")
    public ResponseEntity<String> deleteUser(
    		@PathVariable Long id,
    		@RequestParam @NotBlank(message="email should be valid") String reqPerson){
    	if(service.deleteUser(reqPerson,id)) {
    		return ResponseEntity.ok("User deleted successfully");
    	}else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    	}
    }
    
}