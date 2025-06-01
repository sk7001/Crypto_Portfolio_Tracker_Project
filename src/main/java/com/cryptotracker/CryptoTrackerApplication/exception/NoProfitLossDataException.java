package com.cryptotracker.CryptoTrackerApplication.exception;

public class NoProfitLossDataException extends RuntimeException {
    public NoProfitLossDataException(String message) {
        super(message);
    }
}
// NoProfitLossDataException is used in evaluateAlertTrigger method in PortfolioLossAlertServiceImplS when there are no profit or loss records.
//Also used in ProfitAndLossServiceImpl in getLatest method.