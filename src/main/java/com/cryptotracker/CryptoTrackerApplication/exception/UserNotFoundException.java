package com.cryptotracker.CryptoTrackerApplication.exception;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message) {
		super(message);
	}
}
