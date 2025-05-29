package com.cryptotracker.CryptoTrackerApplication.entity;


import lombok.*;
import jakarta.persistence.*;


@Entity
@Table(name = "crypto_holdings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdingId;
    private Long userId;
    private String coinName;
    private String symbol;
    private Double quantityHeld;
    private Double buyPrice;
    private String buyDate;
}



