package com.cryptotracker.Mappers;

import com.cryptotracker.dto.UserAuthDTO;
import com.cryptotracker.entity.User;

public class UserAuthMapper {
	
	
	//convert UserAuthDTO to UserEntity for login validation
	public static User dtoToEntity(UserAuthDTO dto)
	{
		if (dto==null)
			return null;
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}
	
	//convert UserEntity to UserAuthDTO 
	public static UserAuthDTO userTodto(User user)
	{
		if(user==null)
			return null;
		UserAuthDTO dto = new UserAuthDTO();
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		return dto;
	}

}
