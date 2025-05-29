package com.cryptotracker.CryptoTrackerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptotracker.CryptoTrackerApplication.dto.UserLoginDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.service.UserAuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class UserAuthController {
	
	@Autowired
	private  UserAuthServiceImpl authservice;
	
   //Constructor-based dependency injection for the UserAuthServiceImpl.
    public UserAuthController(UserAuthServiceImpl authservice) {
        this.authservice = authservice;
    }
	
	//function to register user 
    //api/auth/register
	@PostMapping("/register")
	public ResponseEntity<UserAuthDTO> registerUser(@RequestBody @Valid User u){
		return ResponseEntity.ok(authservice.registerUser(u));
	}
	
	//function to login user
	//api/auth/login
	@PostMapping("/login")
	public String loginUser(@RequestBody @Valid UserLoginDTO user) {
		if(authservice.loginUser(user)) {
			return "Successully logged in with email : " + user.getEmail();
		}
		return "Failed Login !!";
	}
}