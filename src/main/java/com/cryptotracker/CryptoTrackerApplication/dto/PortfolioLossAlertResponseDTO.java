package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//PortfolioLossAlertResponseDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioLossAlertResponseDTO {
 private Long id;
 private Long userId;  // Added user ID in response
 private Double lossThresholdPercent;
 private String status;
 private LocalDateTime triggeredAt;
}

