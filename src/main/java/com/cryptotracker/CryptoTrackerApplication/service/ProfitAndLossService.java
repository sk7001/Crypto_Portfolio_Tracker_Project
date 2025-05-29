package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;

public interface ProfitAndLossService {
    ProfitAndLossResponseDTO calculateAndPersist(Long userId);
    ProfitAndLossResponseDTO getLatest(Long userId);
    void calculateAndPersistAll();
}