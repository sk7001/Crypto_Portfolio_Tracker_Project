package com.cryptotracker.CryptoTrackerApplication.exception;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}