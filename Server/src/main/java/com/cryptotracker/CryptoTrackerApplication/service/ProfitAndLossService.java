package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;

public interface ProfitAndLossService {
    ProfitAndLossResponseDTO calculateAndPersist(Long userId);
    ProfitAndLossResponseDTO getLatest(Long userId);
    void calculateAndPersistAll();
}

/*
I define the contract for profit and loss operations in the service layer.
I declare methods to calculate, persist, and fetch PnL results for users.
*/
