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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthServiceInterface{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired 
	private PasswordEncoderUtility passwordEncoder;
	

    // Constructor injection for both dependencies
	/*
	 * public UserAuthServiceImpl(UserRepository userRepo, PasswordEncoderUtility
	 * passwordEncoder) { this.userRepo = userRepo; this.passwordEncoder =
	 * passwordEncoder; }
	 */
	   public UserAuthDTO registerUser(User user)
	   {
		if(userRepo.existsByEmail(user.getEmail())) {
			throw  new RuntimeException("User already exists");
		}else {
			User u = new User();
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setPassword(passwordEncoder.encodeMyRawPassword(user.getPassword()));
			u.setRole(user.getRole());
			userRepo.save(u);

            log.info("Successfully registered the User In !");

			UserAuthDTO responseDto = new UserAuthDTO();
			responseDto.setUserId(u.getUserId());
			responseDto.setPassword("**********");
		    responseDto.setRole(u.getRole());
			responseDto.setEmail(u.getEmail());
			responseDto.setName(u.getName());
			
			return responseDto;
		}
	}
	
	public boolean loginUser(UserLoginDTO userLoginDto) {
		User user = userRepo.findByEmail(userLoginDto.getEmail()) 
				.orElseThrow(() -> new RuntimeException("User not found with email: " + userLoginDto.getEmail()));
        return passwordEncoder.matchMyPasswords(userLoginDto.getPassword(), user.getPassword());
    }
		
}
