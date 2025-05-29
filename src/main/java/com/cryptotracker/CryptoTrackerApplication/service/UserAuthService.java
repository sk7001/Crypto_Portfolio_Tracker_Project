package com.cryptotracker.CryptoTrackerApplication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.util.PasswordEncoderUtility;

@Service
public class UserAuthService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired 
	private PasswordEncoderUtility passwordEncoder;
	
	public User registerUser(User user) {
		if(userRepo.existsByEmail(user.getEmail())) {
			throw  new RuntimeException("User already exists");
		}else {
			User u = new User();
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setPassword(passwordEncoder.encoder().encode(user.getPassword()));
			u.setRole(user.getRole());
			userRepo.save(u);
			return user;
		}
		
		
	}
	
	public boolean loginUser(UserAuthDTO userdto) {
		User user = userRepo.findByEmail(userdto.getEmail()).orElseThrow();
		if(passwordEncoder.encoder().matches(userdto.getPassword(), user.getPassword())){
			return true;
		}
		return false;
		
	}
	
	

}