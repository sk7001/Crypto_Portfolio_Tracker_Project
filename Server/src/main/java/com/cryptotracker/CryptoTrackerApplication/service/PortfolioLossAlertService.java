package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertResponseDTO;

import java.util.List;

public interface PortfolioLossAlertService {
    PortfolioLossAlertResponseDTO createAlert(PortfolioLossAlertRequestDTO request);
    List<PortfolioLossAlertResponseDTO> getAlertsByUserId(Long userId);
    List<PortfolioLossAlertResponseDTO> getAllAlerts();
    void evaluateAlertTrigger();
}
