package com.cryptotracker.CryptoTrackerApplication.exception;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message) {
		super(message);
	}
}
/* this exception is thrown when the user requests for his details from crypto holding table and there is no matching userId present in the user table
 * this is used in cryptoholdingserviceimpl,UserServiceImpl*/