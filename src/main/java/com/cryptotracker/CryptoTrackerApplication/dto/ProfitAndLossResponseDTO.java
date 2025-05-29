package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.*;
import com.cryptotracker.CryptoTrackerApplication.entity.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitAndLossResponseDTO {
    private Long userId;
    private Double totalInvested;
    private Double totalCurrentValue;
    private Double totalProfitLoss;
    private PriceStatus priceStatus;
}