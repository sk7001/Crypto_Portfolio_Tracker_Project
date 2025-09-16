// PortfolioLossAlertResponseDTO.java
package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioLossAlertResponseDTO {

    private Long id; // Unique ID for the alert

    private Long userId; // User associated with this alert

    private Double lossThresholdPercent; // The threshold that triggers the alert

    private String status; // Current status of the alert

    private LocalDateTime triggeredAt; // When the alert was triggered
}
