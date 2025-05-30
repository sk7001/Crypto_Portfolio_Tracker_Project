package com.cryptotracker.CryptoTrackerApplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;
import com.cryptotracker.CryptoTrackerApplication.entity.PriceStatus;
import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import com.cryptotracker.CryptoTrackerApplication.repository.PortfolioLossAlertRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.ProfitAndLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioLossAlertServiceImpl implements PortfolioLossAlertService {
    // Tracks important system events and debugging info
    private static final Logger logger = LoggerFactory.getLogger(PortfolioLossAlertServiceImpl.class);

    @Autowired
    private PortfolioLossAlertRepository alertRepository; // Manages alert database operations

    @Autowired
    private ProfitAndLossRepository pnlRepository; // Handles profit/loss data access

    // Creates new alert entry from user request
    @Override
    public PortfolioLossAlertResponseDTO createAlert(PortfolioLossAlertRequestDTO request) {
        PortfolioLossAlert alert = new PortfolioLossAlert();
        alert.setUserId(request.getUserId());
        alert.setLossThresholdPercent(request.getLossThresholdPercent());
        alert.setStatus("PENDING");
        alert.setTriggeredAt(null);
        PortfolioLossAlert saved = alertRepository.save(alert);
        return toDto(saved);
    }

    // Gets all alerts for specific user, converts to response format
    @Override
    public List<PortfolioLossAlertResponseDTO> getAlertsByUserId(Long userId) {
        List<PortfolioLossAlert> alerts = alertRepository.findAllByUserId(userId);
        List<PortfolioLossAlertResponseDTO> dtos = new ArrayList<>();
        for (PortfolioLossAlert alert : alerts) {
            dtos.add(toDto(alert));
        }
        return dtos;
    }

    // Retrieves all alerts across all users
    @Override
    public List<PortfolioLossAlertResponseDTO> getAllAlerts() {
        List<PortfolioLossAlert> alerts = alertRepository.findAll();
        List<PortfolioLossAlertResponseDTO> dtos = new ArrayList<>();
        for (PortfolioLossAlert alert : alerts) {
            dtos.add(toDto(alert));
        }
        return dtos;
    }

    // Automated check running every 10 seconds for alert conditions
    @Override
    @Scheduled(fixedRate = 10000)
    public void evaluateAlertTrigger() {
        List<PortfolioLossAlert> alerts = alertRepository.findAll();
        for (PortfolioLossAlert alert : alerts) {
            if ("PENDING".equals(alert.getStatus())) {
                List<ProfitAndLoss> pnlList = pnlRepository.findAllByUserId(alert.getUserId());
                double userTotalLoss = 0.0;
                logger.debug("Starting alert evaluation cycle");
                
                for (ProfitAndLoss pnl : pnlList) {
                    if (pnl.getPriceStatus() == PriceStatus.LOSS && pnl.getTotalPortfolio() != null) {
                        userTotalLoss += Math.abs(pnl.getTotalPortfolio());
                    }
                }
                
                if (userTotalLoss >= alert.getLossThresholdPercent()) {
                    alert.setStatus("TRIGGERED");
                    alert.setTriggeredAt(LocalDateTime.now());
                    alertRepository.save(alert);
                }
                logger.debug("Completed pending alerts check");
            }
        }
    }

    // Converts internal alert format to client-friendly version
    private PortfolioLossAlertResponseDTO toDto(PortfolioLossAlert alert) {
        PortfolioLossAlertResponseDTO dto = new PortfolioLossAlertResponseDTO();
        dto.setId(alert.getId());
        dto.setUserId(alert.getUserId());
        dto.setLossThresholdPercent(alert.getLossThresholdPercent());
        dto.setStatus(alert.getStatus());
        dto.setTriggeredAt(alert.getTriggeredAt());
        return dto;
    }
}
