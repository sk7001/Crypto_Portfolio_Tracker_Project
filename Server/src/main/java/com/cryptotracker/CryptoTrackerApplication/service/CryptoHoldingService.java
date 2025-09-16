package com.cryptotracker.CryptoTrackerApplication.service;


import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;

import java.util.List;


public interface CryptoHoldingService {
	CryptoHolding addCryptoHolding(CryptoDTO dto);
    List<CryptoHolding> getCryptoHoldingsByUserId(Long userId); // Make sure this is included
    CryptoHolding getCryptoHoldingById(Long holdingId);
    CryptoHolding updateCryptoHolding(Long holdingId, CryptoDTO dto);
    void deleteCryptoHolding(Long holdingId);
    List<CryptoHolding> getAllCryptoHoldings(Long userId);

}

