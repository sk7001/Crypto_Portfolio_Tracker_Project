package com.cryptotracker.CryptoTrackerApplication.service;

import java.util.List;

import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;

public interface UserServiceInterface
{
	public  List<UserDTO> getAllUser();
	public boolean updateUserRole(String reqPersonMail,Long userId, String role);
	public boolean deleteUser(String reqPersonMail,Long userid) ;
	public User getbyEmail(String email);
}