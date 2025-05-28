package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.CryptoTrackerApplication.dto.HoldingResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.repository.HoldingRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.PriceRepository;
import com.cryptotracker.model.CryptoHolding;
import com.cryptotracker.model.CryptoPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculationService{

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private PriceRepository priceRepository;

    public List<HoldingResponseDTO> getPortfolioSummary() {
        List<CryptoHolding> holdings = holdingRepository.findAll();
        List<HoldingResponseDTO> responseList = new ArrayList<>();

        for (CryptoHolding holding : holdings) {
            CryptoPrice price = priceRepository.findById(holding.getSymbol()).orElse(null);

            if (price != null) {
                double currentValue = holding.getQuantity() * price.getPrice();
                double totalBuyPrice = holding.getQuantity() * holding.getAverageBuyPrice();
                double pnl = currentValue - totalBuyPrice;

                HoldingResponseDTO dto = new HoldingResponseDTO(
                        holding.getSymbol(),
                        holding.getQuantity(),
                        holding.getAverageBuyPrice(),
                        price.getPrice(),
                        currentValue,
                        pnl
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }
}