package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CryptoDTO {
    private Long holdingId; 
    private Long userId;
    private String coinName;
    private String symbol;
    private Double quantityHeld;
    private Double buyPrice;
    private String buyDate; 
}

