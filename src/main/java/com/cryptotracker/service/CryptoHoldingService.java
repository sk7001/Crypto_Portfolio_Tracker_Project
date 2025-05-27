package com.cryptotracker.service;


import com.cryptotracker.dto.CryptoHoldingRequest;
import com.cryptotracker.dto.CryptoHoldingResponse;

import java.util.List;

public interface CryptoHoldingService {
    CryptoHoldingResponse addCryptoHolding(Long userId, CryptoHoldingRequest request);
    List<CryptoHoldingResponse> getHoldingsByUser(Long userId);
}

