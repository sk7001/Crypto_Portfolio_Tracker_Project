package com.cryptotracker.CryptoTrackerApplication.exception;


public class CryptoAssetNotFoundException extends RuntimeException {
    public CryptoAssetNotFoundException(String message) {
        super(message);
    }
}
/*
this class deals with the assets which are not created by post method and is not in the database already
*/