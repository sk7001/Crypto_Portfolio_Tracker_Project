package com.cryptotracker.service;

import com.cryptotracker.dto.AlertsDTO;
import com.cryptotracker.entity.Alerts;

import java.util.List;

public interface AlertsService {
	Alerts addAlert(AlertsDTO dto);
    List<Alerts> getAlerts(Long userID);
    List<Alerts> getAllAlerts();
}
