package com.cryptotracker.CryptoTrackerApplication.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
//This exception is used in UserAuthServiceImpl to handle incorrect passwords.