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
/*
I encapsulate all the profit and loss result data for a user.
I include userId, investment, profit/loss amount, and the status, with Lombok handling boilerplate.
*/
