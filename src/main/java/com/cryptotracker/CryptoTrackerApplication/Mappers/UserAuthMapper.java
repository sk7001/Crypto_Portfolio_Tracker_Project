package com.cryptotracker.CryptoTrackerApplication.Mappers;

import org.springframework.stereotype.Component;

import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;

@Component
public class UserAuthMapper {
	public UserAuthDTO userEntityToUserAuthDTO (User user) {
		UserAuthDTO u = new UserAuthDTO();
		u.setUserId(user.getUserId());
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		u.setRole(user.getRole());
		return u;	
	}
	public User userAuthDtoToEntity(UserAuthDTO userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		user.setUserId(user.getUserId());
		return user;
	}
}