package com.cryptotracker.CryptoPortfolioTrackerApplication.service;

import com.cryptotracker.CryptoPortfolioTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoPortfolioTrackerApplication.entity.Alerts;

import java.util.List;

public interface AlertsService {
	Alerts addAlert(AlertsDTO dto);
    List<Alerts> getAlerts(Long userID);
    List<Alerts> getAllAlerts();
    List<Alerts> getPendingAlerts(Long userId);
    List<Alerts> getTriggeredAlerts(Long userId);
}
