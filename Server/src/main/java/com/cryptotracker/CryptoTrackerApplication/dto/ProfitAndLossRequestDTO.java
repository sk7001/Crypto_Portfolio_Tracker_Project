package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.*;

// DTO for accepting manual create/update requests if needed
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitAndLossRequestDTO {
    private Long userId;
}
/*
I represent the data needed when creating or updating a user's profit and loss record.
I simply hold the userId, and Lombok generates my boilerplate code.
*/
