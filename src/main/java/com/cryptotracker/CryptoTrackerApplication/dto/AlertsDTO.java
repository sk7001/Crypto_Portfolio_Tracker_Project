package com.cryptotracker.CryptoPortfolioTrackerApplication.dto;

import com.cryptotracker.CryptoPortfolioTrackerApplication.entity.AlertsDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertsDTO {
    private Long userId;
    private String symbol;
    private Double triggerPrice;
    private AlertsDirection direction;
}


// I have created the Alerts DTO for Alerts that we can receive or send through APIs.