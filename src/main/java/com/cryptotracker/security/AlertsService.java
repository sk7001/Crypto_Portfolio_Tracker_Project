package com.cryptotracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptotracker.dto.AlertsDTO;
import com.cryptotracker.entity.Alerts;
import com.cryptotracker.entity.AlertsStatus;
import com.cryptotracker.repository.AlertsRepository;

@Service
public class AlertsService {

	@Autowired
	private AlertsRepository alertRepository;
	
	public Alerts addAlert(AlertsDTO dto) {
    	Alerts alert = new Alerts(dto.getUserId(), dto.getSymbol(), dto.getTriggerPrice(), dto.getDirection(), AlertsStatus.PENDING);
		return alertRepository.save(alert);
	}
	//addAlert method is called to create a new alert object and then save it in the database(I mean in alertRepository)
	
	public List<Alerts> getAlerts(Long userID) {
		return alertRepository.findByUserId(userID);
	}
	//getAlerts method is called to fetch all the saved alerts by the user with the userID.
}
