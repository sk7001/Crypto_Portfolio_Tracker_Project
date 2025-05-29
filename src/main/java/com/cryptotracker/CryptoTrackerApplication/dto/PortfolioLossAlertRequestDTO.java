package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioLossAlertRequestDTO {
    private Long userId;
    private Double lossThresholdPercent; // camelCase matches entity
}
