package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.exception.CryptoAssetNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.exception.PriceFetchException;
import com.cryptotracker.CryptoTrackerApplication.exception.UserNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoHoldingRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoHoldingServiceImpl implements CryptoHoldingService {

	@Autowired
	private CryptoPriceRepository priceRepository;
	
    @Autowired
    private CryptoHoldingRepository repository;

    @Override
    public CryptoHolding addCryptoHolding(CryptoDTO dto) {
    	CryptoPrice cryptoPrice = priceRepository.findBySymbol1(dto.getSymbol());
        CryptoHolding holding = new CryptoHolding(
            null,
            dto.getUserId(),
            dto.getCoinName(),
            dto.getSymbol(),
            dto.getQuantityHeld(),
            cryptoPrice.getPrice(),
            dto.getBuyDate()
        );
        return repository.save(holding);
    }


    @Override
    public List<CryptoHolding> getCryptoHoldingsByUserId(Long userId) {
        List<CryptoHolding> holdings = repository.findByUserId(userId);
        if (holdings.isEmpty()) {
            throw new UserNotFoundException("No crypto holdings found for user with ID: " + userId);
        }
        return holdings;
    }

    @Override
    public CryptoHolding getCryptoHoldingById(Long holdingId) {
        return repository.findById(holdingId)
                .orElseThrow(() -> new CryptoAssetNotFoundException("Crypto holding not found with ID: " + holdingId));
    }

    @Override
    public CryptoHolding updateCryptoHolding(Long holdingId, CryptoDTO dto) {
        CryptoPrice cryptoPrice = priceRepository.findBySymbol1(dto.getSymbol());
        
        if (cryptoPrice == null) {
            throw new PriceFetchException("Price not found for symbol: " + dto.getSymbol());
        }

        CryptoHolding holding = getCryptoHoldingById(holdingId);
        
        // NOTE: Do NOT update userId here
        holding.setCoinName(dto.getCoinName());
        holding.setSymbol(dto.getSymbol());
        holding.setQuantityHeld(dto.getQuantityHeld());
        holding.setBuyPrice(cryptoPrice.getPrice());
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
