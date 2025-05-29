package com.cryptotracker.CryptoTrackerApplication.exception;

public class InvalidInputException extends RuntimeException{
	public InvalidInputException(String message) {
		super(message);
	}
}
/* this exception is thrown when user inputs different roles other than ADMIN and USER as they are the defined ones
 
 */