// PortfolioLossAlertRequestDTO.java
package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioLossAlertRequestDTO {

    private Long userId; // The user for whom the alert is being set

    private Double lossThresholdPercent; // Threshold value to trigger the alert
}
