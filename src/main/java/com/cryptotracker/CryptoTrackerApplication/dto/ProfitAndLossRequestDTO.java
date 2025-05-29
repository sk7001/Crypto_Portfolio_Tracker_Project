package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.*;

// DTO for accepting manual create/update requests if needed
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitAndLossRequestDTO {
    private Long userId;
}