package com.cryptotracker.CryptoTrackerApplication.entity;

public enum PriceStatus {
    PROFIT,
    LOSS,
    NEUTRAL
}

/*
I define the possible profit and loss status values for a user's portfolio.
I ensure only valid states—PROFIT, LOSS, or NEUTRAL—can be used throughout the codebase.
*/
