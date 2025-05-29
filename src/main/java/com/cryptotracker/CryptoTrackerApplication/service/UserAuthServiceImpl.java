package com.cryptotracker.CryptoTrackerApplication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptotracker.CryptoTrackerApplication.dto.UserLoginDTO;
import com.cryptotracker.CryptoTrackerApplication.service.UserAuthServiceInterface;
import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.exception.EmailAlreadyExistsException;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.util.PasswordEncoderUtility;

@Service
public class UserAuthServiceImpl implements UserAuthServiceInterface{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired 
	private PasswordEncoderUtility passwordEncoder;
	
	 public UserAuthServiceImpl(UserRepository userRepo) {
	        this.userRepo = userRepo;
	    }
	//register the user and store encoded password in DB
	public UserAuthDTO registerUser(User user) {
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("User already exists with email: " + user.getEmail());
		}else {
			User u = new User();
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setPassword(passwordEncoder.encodeMyRawPassword(user.getPassword()));
			u.setRole(user.getRole());
			userRepo.save(u);

			UserAuthDTO responseDto = new UserAuthDTO();
			responseDto.setUserId(u.getUserId());
			responseDto.setPassword("");
		    responseDto.setRole(u.getRole());
			responseDto.setEmail(u.getEmail());
			responseDto.setName(u.getName());
			return responseDto;
		}
	}
	//login with the user ,validates password 
	public boolean loginUser(UserLoginDTO userLoginDto) {
		User user = userRepo.findByEmail(userLoginDto.getEmail()) 
				.orElseThrow(() -> new RuntimeException("User not found with email: " + userLoginDto.getEmail()));
        return passwordEncoder.matchMyPasswords(userLoginDto.getPassword(), user.getPassword());
    }
		
}