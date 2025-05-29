package com.cryptotracker.CryptoTrackerApplication.exception;

public class DuplicateAlertException extends RuntimeException {
    public DuplicateAlertException(String message) {
        super(message);
    }
}
/*
this class deals with the alerts which are already created by post method and are stored in database,
 then when the user tries to post the same alert again this exception will be called.
*/