package com.cryptotracker.CryptoTrackerApplication.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
// InvalidInputException is used in PortfolioLossAlertServiceImpl in createAlert method.