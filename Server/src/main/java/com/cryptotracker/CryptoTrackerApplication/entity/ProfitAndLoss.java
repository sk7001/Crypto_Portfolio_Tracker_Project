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

    @Enumerated(EnumType.STRING)
    private PriceStatus priceStatus;

    private Double totalPortfolio;
}

/*
I represent the profit and loss record for each user in the database.
I store the userId, PnL status, and total profit or loss, with JPA and Lombok handling persistence and boilerplate code.
*/
