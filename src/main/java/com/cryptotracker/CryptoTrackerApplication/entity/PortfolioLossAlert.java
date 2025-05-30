package com.cryptotracker.CryptoTrackerApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioLossAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each alert

    private Long userId; // User this alert is associated with

    @Column(name = "loss_threshold_percent") // Maps to the correct DB column
    private double lossThresholdPercent; // Threshold to trigger the alert

    private String status = "PENDING"; // Current status of the alert

    private LocalDateTime triggeredAt; // When the alert was triggered
}
