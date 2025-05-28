package com.cryptotracker.CryptoTrackerApplication.security;

import com.cryptotracker.CryptoTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Alerts;

import java.util.List;

public interface IAlertsService {
	Alerts addAlert(AlertsDTO dto);
    List<Alerts> getAlerts(Long userID);
}
