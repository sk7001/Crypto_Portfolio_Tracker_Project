package com.cryptotracker.CryptoTrackerApplication.exception;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}
/*
this exception is thrown when the user registers with the same email again which is already registered with the database 
*/