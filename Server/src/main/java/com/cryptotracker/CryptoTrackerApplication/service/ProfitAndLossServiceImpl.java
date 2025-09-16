package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.entity.*;
import com.cryptotracker.CryptoTrackerApplication.exception.CryptoAssetNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.exception.NoHoldingsFoundException;
import com.cryptotracker.CryptoTrackerApplication.exception.NoProfitLossDataException;
import com.cryptotracker.CryptoTrackerApplication.repository.*;
import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ProfitAndLossServiceImpl implements ProfitAndLossService {

    private static final Logger logger = LoggerFactory.getLogger(ProfitAndLossServiceImpl.class);

    @Autowired
    private CryptoHoldingRepository holdingRepo;
    @Autowired
    private CryptoPriceRepository priceRepo;
    @Autowired
    private ProfitAndLossRepository pnlRepo;

    @Scheduled(fixedRate = 60000)
    public void calculateAndPersistAll() {
        logger.info("Scheduled task started: calculating PnL for all users.");

        List<Long> userIds = holdingRepo.findAllDistinctUserIds();
        logger.debug("Found {} unique user IDs.", userIds.size());

        for (Long userId : userIds) {
            try {
                calculateAndPersist(userId);
                logger.info("PnL calculated and saved for userId: {}", userId);
            } catch (Exception e) {
                logger.error("Error calculating PnL for userId: {}", userId, e);
            }
        }

        logger.info("Scheduled PnL update completed.");
    }

    @Override
    public ProfitAndLossResponseDTO calculateAndPersist(Long userId) {
        logger.debug("Calculating PnL for userId: {}", userId);

        List<CryptoHolding> holdings = holdingRepo.findByUserId(userId);
        if (holdings.isEmpty()) {
            logger.warn("No holdings found for userId: {}", userId);
            throw new NoHoldingsFoundException("No holdings found for userId: " + userId);
        }

        double totalInvested = 0.0;
        double totalCurrentValue = 0.0;

        for (CryptoHolding holding : holdings) {
            double qty = holding.getQuantityHeld();
            double buyPrice = holding.getBuyPrice();
            String symbol = holding.getSymbol();

            totalInvested += qty * buyPrice;

            double currentPrice = priceRepo.findById(symbol)
                    .map(CryptoPrice::getPrice)
                    .orElseThrow(() -> new CryptoAssetNotFoundException("Price not found for symbol: " + symbol));

            totalCurrentValue += qty * currentPrice;

            logger.debug("Holding: symbol={}, qty={}, buyPrice={}, currentPrice={}", 
                         symbol, qty, buyPrice, currentPrice);
        }

        double profitLoss = totalCurrentValue - totalInvested;
        PriceStatus status = (profitLoss > 0) ? PriceStatus.PROFIT :
                             (profitLoss < 0) ? PriceStatus.LOSS : PriceStatus.NEUTRAL;

        logger.info("Computed PnL for userId: {} | Invested: {} | Current: {} | PnL: {} | Status: {}",
                userId, totalInvested, totalCurrentValue, profitLoss, status);

        Optional<ProfitAndLoss> opt = pnlRepo.findByUserId(userId);
        ProfitAndLoss entity = opt.orElseGet(ProfitAndLoss::new);
        entity.setUserId(userId);
        entity.setTotalPortfolio(profitLoss);
        entity.setPriceStatus(status);

        pnlRepo.save(entity);
        logger.debug("Saved PnL entity to DB for userId: {}", userId);

        return new ProfitAndLossResponseDTO(userId, totalInvested, totalCurrentValue, profitLoss, status);
    }

    @Override
    public ProfitAndLossResponseDTO getLatest(Long userId) {
        logger.debug("Fetching latest PnL for userId: {}", userId);

        return pnlRepo.findByUserId(userId)
            .map(e -> {
                logger.info("Latest PnL fetched for userId: {} | Status: {}", userId, e.getPriceStatus());
                return new ProfitAndLossResponseDTO(
                    e.getUserId(),
                    null,
                    null,
                    e.getTotalPortfolio(),
                    e.getPriceStatus());
            })
            .orElseThrow(() -> {
                logger.warn("No PnL record found for userId: {}", userId);
                return new NoProfitLossDataException("No profit/loss data available for user ID: " + userId);
            });
    }
}
/*
I implement all profit and loss operations for users.
I calculate and persist PnL, fetch the latest results, and also schedule automatic updates for all users every minute.
I rely on repository classes for database interactions and keep all business logic here for clean separation from the controller.
*/
