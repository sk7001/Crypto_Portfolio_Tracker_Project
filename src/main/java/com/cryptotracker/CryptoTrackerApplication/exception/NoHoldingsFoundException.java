package com.cryptotracker.CryptoTrackerApplication.exception;

public class NoHoldingsFoundException extends RuntimeException {
    public NoHoldingsFoundException(String message) {
        super(message);
    }
}
// This exception is used in ProfitAndLossServiceImpl when there are no holdings for a particular user.