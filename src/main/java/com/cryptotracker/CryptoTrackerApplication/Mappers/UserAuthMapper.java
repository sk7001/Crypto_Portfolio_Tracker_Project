package com.cryptotracker.CryptoTrackerApplication.Mappers;

import org.springframework.stereotype.Component;

import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;

@Component
public class UserAuthMapper {
	
	
	 // Converts User entity to UserAuthDTO for authentication purposes
	public UserAuthDTO userEntityToUserAuthDTO (User user) {
		UserAuthDTO u = new UserAuthDTO();
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		return u;	
	}
	
	// Converts UserAuthDTO to User entity for authentication or business logic processing

	public User userAuthDtoToEntity(UserAuthDTO udto) {
		User user = new User();
		user.setEmail(udto.getEmail());
		user.setPassword(udto.getPassword());
		return user;
	}

}