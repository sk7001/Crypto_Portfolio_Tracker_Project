package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.exception.CryptoAssetNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.exception.PriceFetchException;
import com.cryptotracker.CryptoTrackerApplication.exception.UserNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoHoldingRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CryptoHoldingServiceImpl implements CryptoHoldingService {

    private static final Logger logger = LoggerFactory.getLogger(CryptoHoldingServiceImpl.class);

    @Autowired
    private CryptoPriceRepository priceRepository;

    @Autowired
    private CryptoHoldingRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public CryptoHolding addCryptoHolding(CryptoDTO dto) {
        logger.info("Adding new crypto holding for user ID: {}", dto.getUserId());
        CryptoPrice cryptoPrice = priceRepository.findBySymbol1(dto.getSymbol());

        if (cryptoPrice == null) {
            logger.error("Price not found for symbol: {}", dto.getSymbol());
            throw new PriceFetchException("Price not found for symbol: " + dto.getSymbol());
        }

        CryptoHolding holding = new CryptoHolding(
                null,
                dto.getUserId(),
                dto.getSymbol(),
                dto.getQuantityHeld(),
                cryptoPrice.getPrice(),
                LocalDateTime.now()
        );

        CryptoHolding saved = repository.save(holding);
        logger.info("Successfully saved crypto holding with ID: {}", saved.getHoldingId());
        return saved;
    }

    @Override
    public List<CryptoHolding> getCryptoHoldingsByUserId(Long userId) {
        logger.info("Fetching crypto holdings for user ID: {}", userId);
        List<CryptoHolding> holdings = repository.findByUserId(userId);
        if (holdings.isEmpty()) {
            logger.warn("No holdings found for user ID: {}", userId);
            throw new UserNotFoundException("No crypto holdings found for user with ID: " + userId);
        }
        return holdings;
    }

    @Override
    public CryptoHolding getCryptoHoldingById(Long holdingId) {
        logger.info("Fetching crypto holding by ID: {}", holdingId);
        return repository.findById(holdingId)
                .orElseThrow(() -> {
                    logger.warn("Crypto holding not found with ID: {}", holdingId);
                    return new CryptoAssetNotFoundException("Crypto holding not found with ID: " + holdingId);
                });
    }

    @Override
    public CryptoHolding updateCryptoHolding(Long holdingId, CryptoDTO dto) {
        logger.info("Updating crypto holding ID: {}", holdingId);

        CryptoPrice cryptoPrice = priceRepository.findBySymbol1(dto.getSymbol());

        if (cryptoPrice == null) {
            logger.error("Price not found for symbol: {}", dto.getSymbol());
            throw new PriceFetchException("Price not found for symbol: " + dto.getSymbol());
        }

        CryptoHolding holding = getCryptoHoldingById(holdingId);

        holding.setSymbol(dto.getSymbol());
        holding.setQuantityHeld(dto.getQuantityHeld());
        holding.setBuyPrice(cryptoPrice.getPrice());
        holding.setBuyDate(LocalDateTime.now());

        CryptoHolding updated = repository.save(holding);
        logger.info("Successfully updated crypto holding ID: {}", updated.getHoldingId());
        return updated;
    }

    @Override
	public List<CryptoHolding> getAllCryptoHoldings(Long userId) {
		logger.info("Admin check before fetching all crypto holdings for user ID: {}", userId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		if (Role.ADMIN!=user.getRole()) {
			logger.warn("Unauthorized access attempt by user ID: {} with role: {}", userId, user.getRole());
			throw new RuntimeException("Access denied: Only ADMIN users can access all holdings.");
		}

		logger.info("User ID: {} is ADMIN, proceeding to fetch all holdings", userId);
		return repository.findAll();
}



    @Override
    public void deleteCryptoHolding(Long holdingId) {
        logger.info("Deleting crypto holding with ID: {}", holdingId);
        if (!repository.existsById(holdingId)) {
            logger.warn("Crypto holding not found with ID: {}", holdingId);
            throw new RuntimeException("Cannot delete. Crypto holding not found with ID: " + holdingId);
        }
        repository.deleteById(holdingId);
        logger.info("Deleted crypto holding with ID: {}", holdingId);
    }
}