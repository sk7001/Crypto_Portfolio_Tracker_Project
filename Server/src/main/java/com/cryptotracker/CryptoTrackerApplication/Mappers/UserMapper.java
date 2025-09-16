package com.cryptotracker.CryptoTrackerApplication.Mappers;

import org.springframework.stereotype.Component;

import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;



@Component
public class UserMapper {
	

//Converts Userentity object to UserDTO that can be safely exposed to the client or used in service layers without exposing sensitive details.
	public static UserDTO userToDto(User user) {
		UserDTO u = new UserDTO();
		u.setEmail(user.getEmail());
		u.setName(user.getName());
		u.setRole(user.getRole());
		u.setUserId(user.getUserId());
		return u;	
	}
	
	//Converts a UserDTO object to a User entity.
    // This method is used when receiving data from the client or service layer
	public static User userDtoToUser(UserDTO userdto) {
		User u = new User();
		u.setEmail(userdto.getEmail());
		u.setName(userdto.getName());
		u.setRole(userdto.getRole());
		u.setUserId(userdto.getUserId());
		return u;	
	}
}

