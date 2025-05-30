package com.cryptotracker.CryptoTrackerApplication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cryptotracker.CryptoTrackerApplication.Mappers.UserMapper;
import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.exception.UserNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserServiceInterface {
	@Autowired
    private  UserRepository userRepository;
	
	// the method  returns all the users ,or else if the role is not Admin ,then the method throws exception
    public List<UserDTO> getAllUser(){

        return userRepository.findAll().stream() .map(UserMapper::userToDto)
                   .collect(Collectors.toList());
        }
    
    public boolean isAdmin(String email) {
	    User reqUser = userRepository.findByEmail(email)
	                          .orElseThrow(() -> new RuntimeException("User not found"));
	    return reqUser.getRole() == Role.ADMIN;
	}
 
	
	
	//This method finds the user by userId, if found checks if the role is Admin,if Admin ,then updates the user role and if user is not Admin ,then the method throws exceptions
	 public boolean updateUserRole(String reqPersonMail,Long userId, String role){
	    	try {
	        User u=userRepository.findByEmail(reqPersonMail).orElseThrow();
	        User user = userRepository.findById(userId).orElseThrow();
	        if(u.getRole() == Role.ADMIN) {
	        	user.setRole(Role.valueOf(role.toUpperCase()));
	            userRepository.save(u);
	            log.info("User updated successfully");
	            return true;
	        }
	    	}
	        catch(Exception e) {
	        	System.out.println("Error while updating user role: " + e.getMessage());
	        }
	        return false;
	    }
    
    //Deletes the user only when the role is Admin 

    	 public boolean deleteUser(String reqPersonMail,Long userid) {
     		User u=userRepository.findByEmail(reqPersonMail).orElseThrow(
     				()-> new RuntimeException("Admin with mail : " + reqPersonMail + " doesnt exist !")
     				);
     		if(u.getRole()!=Role.ADMIN)
     			System.out.println("Only Admin can delete the user!");
     		
     		
     		if (!userRepository.existsById(userid)) {
     	        log.error("User with id: " + userid + " does not exist!");
     	        throw new RuntimeException("User with id: " + userid + " does not exist!");
     	    }
     	    userRepository.deleteById(userid);
     	    log.info("User is deleted by Admin");
     	    return true;
    	 }
     	    
     
    
    //Finds the user by user email
    public User getbyEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }


    
}