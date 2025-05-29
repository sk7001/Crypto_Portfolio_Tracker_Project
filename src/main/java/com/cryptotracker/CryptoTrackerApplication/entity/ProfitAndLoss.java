package com.cryptotracker.CryptoTrackerApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profit_and_loss")
public class ProfitAndLoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to User
    private Long userId;

    // Enum status (PROFIT, LOSS, NEUTRAL)
    @Enumerated(EnumType.STRING)
    private PriceStatus priceStatus;

    // Store the total portfolio profit or loss value
    private Double totalPortfolio;
}