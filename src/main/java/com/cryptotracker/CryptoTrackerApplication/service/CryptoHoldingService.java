package com.cryptotracker.CryptoTrackerApplication.service;


import java.util.List;

import com.cryptotracker.CryptoTrackerApplication.dto.CryptoHoldingRequest;
import com.cryptotracker.CryptoTrackerApplication.dto.CryptoHoldingResponse;

public interface CryptoHoldingService {
    CryptoHoldingResponse addCryptoHolding(Long userId, CryptoHoldingRequest request);
    List<CryptoHoldingResponse> getHoldingsByUser(Long userId);
}

