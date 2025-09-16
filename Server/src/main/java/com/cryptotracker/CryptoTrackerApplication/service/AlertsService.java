package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Alerts;

import java.util.List;

public interface AlertsService {
	Alerts addAlert(AlertsDTO dto);
    List<Alerts> getAlerts(Long userID);
    List<Alerts> getAllAlerts(Long userId);
    List<Alerts> getPendingAlerts(Long userId);
    List<Alerts> getTriggeredAlerts(Long userId);
}
