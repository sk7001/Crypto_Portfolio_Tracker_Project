package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.model.PortfolioSummary;
import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;
import com.cryptotracker.CryptoTrackerApplication.repository.PortfolioLossAlertRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.PortfolioSummaryRepository;
import com.cryptotracker.model.PnLStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class PortfolioLossAlertService {
    @Autowired
    private PortfolioLossAlertRepository lossAlertRepo;
    @Autowired
    private PortfolioSummaryRepository summaryRepo;
    public String createAlert(PortfolioLossAlert alert) {
        lossAlertRepo.save(alert);
        return "Loss threshold alert created.";
    }
    public List<PortfolioLossAlert> getUserAlerts(Long userId) {
        return lossAlertRepo.findByUserId(userId);
    }
    @Scheduled(fixedRate = 30000)
    public void checkLossAlerts() {
        List<PortfolioLossAlert> alerts = lossAlertRepo.findByStatus("PENDING");
        for (PortfolioLossAlert alert : alerts) {
            List<PortfolioSummary> summaries = summaryRepo.findAll();
            double totalLoss = summaries.stream()
                    .filter(s -> s.getUserId().equals(alert.getUserId()) && s.getStatus() == PnLStatus.LOSS)
                    .mapToDouble(PortfolioSummary::getPnlValue)
                    .sum();
            double totalPortfolioValue = summaries.stream()
                    .filter(s -> s.getUserId().equals(alert.getUserId()))
                    .mapToDouble(s -> Math.abs(s.getPnlValue()))
                    .sum();
            double lossPercent = (totalPortfolioValue == 0) ? 0 : (Math.abs(totalLoss) / totalPortfolioValue) * 100;
            if (lossPercent >= alert.getLossThresholdpercent()) {
                alert.setStatus("TRIGGERED");
                alert.setTriggeredAt(LocalDateTime.now());
                lossAlertRepo.save(alert);
            }
        }
    }
}
