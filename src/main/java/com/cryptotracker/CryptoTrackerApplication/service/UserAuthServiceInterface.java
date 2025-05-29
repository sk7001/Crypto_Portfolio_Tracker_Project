package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.UserLoginDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.User;

public interface UserAuthServiceInterface{
	public UserAuthDTO registerUser(User user);
	public boolean loginUser(UserLoginDTO userLoginDto);
}