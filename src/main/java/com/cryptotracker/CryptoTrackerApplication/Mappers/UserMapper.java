package com.cryptotracker.CryptoTrackerApplication.Mappers;

import com.cryptotracker.CryptoTrackerApplication.dto.UserRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.UserResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;

public class UserMapper {
	
	//convert UserRequestDTO to UserEntity for create / update
	public static User dtoToEntity(UserRequestDTO dto)
	{
	      if (dto == null)
	    	  return null;
            User user = new User();
	        user.setName(dto.getName());
	        user.setEmail(dto.getEmail());
	        user.setPassword(dto.getPassword());
	        user.setRole(dto.getRole());
	        return user;
	    }
	//convert UserEntity to UserResponseDTO when sending user data back to the client
	public static UserResponseDTO EntityTodto(User user)
	{
		if(user==null)
			return null;
		UserResponseDTO dto = new UserResponseDTO();
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
		
	}
}


