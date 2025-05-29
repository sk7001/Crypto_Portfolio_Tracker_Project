package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoHoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoHoldingServiceImpl implements CryptoHoldingService {

    @Autowired
    private CryptoHoldingRepository repository;

    @Override
    public CryptoHolding addCryptoHolding(CryptoDTO dto) {
        CryptoHolding holding = new CryptoHolding(
            null,
            dto.getUserId(),
            dto.getCoinName(),
            dto.getSymbol(),
            dto.getQuantityHeld(),
            dto.getBuyPrice(),
            dto.getBuyDate()
        );
        return repository.save(holding);
    }


    @Override
    public List<CryptoHolding> getCryptoHoldingsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public CryptoHolding getCryptoHoldingById(Long holdingId) {
        return repository.findById(holdingId)
                .orElseThrow(() -> new RuntimeException("Crypto holding not found with ID: " + holdingId));
    }

    @Override
    public CryptoHolding updateCryptoHolding(Long holdingId, CryptoDTO dto) {
        CryptoHolding holding = getCryptoHoldingById(holdingId);
        // NOTE: Do NOT update userId here
        holding.setCoinName(dto.getCoinName());
        holding.setSymbol(dto.getSymbol());
        holding.setQuantityHeld(dto.getQuantityHeld());
        holding.setBuyPrice(dto.getBuyPrice());
        holding.setBuyDate(dto.getBuyDate());
        return repository.save(holding);
    }

    @Override
    public List<CryptoHolding> getAllCryptoHoldings() {
        return repository.findAll();
    }

    @Override
    public void deleteCryptoHolding(Long holdingId) {
        if (!repository.existsById(holdingId)) {
            throw new RuntimeException("Cannot delete. Crypto holding not found with ID: " + holdingId);
        }
        repository.deleteById(holdingId);
    }
    
    
}
