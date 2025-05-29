package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.entity.*;
import com.cryptotracker.CryptoTrackerApplication.repository.*;
import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfitAndLossServiceImpl {

    @Autowired
    private CryptoHoldingRepository holdingRepo;
    @Autowired
    private CryptoPriceRepository priceRepo;
    @Autowired
    private ProfitAndLossRepository pnlRepo;

    // Scheduled batch update for all users (no-arg, Spring-compliant)
    @Scheduled(fixedRate = 60000)
    public void calculateAndPersistAll() {
        List<Long> userIds = holdingRepo.findAllDistinctUserIds();
        for (Long userId : userIds) {
            calculateAndPersist(userId);
        }
    }

    // Calculate/save PnL for a user (API use/manual call)
    public ProfitAndLossResponseDTO calculateAndPersist(Long userId) {
        List<CryptoHolding> holdings = holdingRepo.findByUserId(userId);

        double totalInvested = 0.0;
        double totalCurrentValue = 0.0;

        for (CryptoHolding holding : holdings) {
            double qty = holding.getQuantityHeld();
            double buyPrice = holding.getBuyPrice();
            String symbol = holding.getSymbol();

            totalInvested += qty * buyPrice;

            double currentPrice = priceRepo.findById(symbol)
                    .map(CryptoPrice::getPrice)
                    .orElse(0.0);
            totalCurrentValue += qty * currentPrice;
        }
        double profitLoss = totalCurrentValue - totalInvested;

        PriceStatus status = (profitLoss > 0) ? PriceStatus.PROFIT : (profitLoss < 0 ? PriceStatus.LOSS : PriceStatus.NEUTRAL);

        // Save/update entity
        Optional<ProfitAndLoss> opt = pnlRepo.findByUserId(userId);
        ProfitAndLoss entity = opt.orElseGet(ProfitAndLoss::new);
        entity.setUserId(userId);
        entity.setTotalPortfolio(profitLoss);   // field name assumed corrected in entity
        entity.setPriceStatus(status);          // field name assumed corrected in entity

        pnlRepo.save(entity);

        return new ProfitAndLossResponseDTO(userId, totalInvested, totalCurrentValue, profitLoss, status);
    }

    // Get latest record for user
    public ProfitAndLossResponseDTO getLatest(Long userId) {
        return pnlRepo.findByUserId(userId)
            .map(e -> new ProfitAndLossResponseDTO(
                e.getUserId(),
                null, // totalInvested, if not stored, left null
                null, // totalCurrentValue, if not stored, left null
                e.getTotalPortfolio(),
                e.getPriceStatus()))
            .orElse(null);
    }
}