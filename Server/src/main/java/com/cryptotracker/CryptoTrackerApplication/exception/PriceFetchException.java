package com.cryptotracker.CryptoTrackerApplication.exception;

public class PriceFetchException extends RuntimeException {
    public PriceFetchException(String message) {
        super(message);
    }
}
/* this exception handles when there is no price generated for the price field
 * used in CryptoHoldingServiceImpl in updateCryptoHolding
*/