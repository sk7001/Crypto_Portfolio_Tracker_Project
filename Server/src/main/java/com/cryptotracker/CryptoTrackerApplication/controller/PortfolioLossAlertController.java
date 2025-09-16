package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.service.PortfolioLossAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loss-alerts")
public class PortfolioLossAlertController {

    @Autowired
    private PortfolioLossAlertService alertService; // Handles business logic for alerts

    // Creates a new portfolio loss alert for a user
    @PostMapping
    public PortfolioLossAlertResponseDTO createAlert(@RequestBody PortfolioLossAlertRequestDTO request) {
        return alertService.createAlert(request);
    }

    // Retrieves all alerts for a specific user
    @GetMapping("/user/{userId}")
    public List<PortfolioLossAlertResponseDTO> getAlertsByUser(@PathVariable Long userId) {
        return alertService.getAlertsByUserId(userId);
    }

    // Retrieves all alerts in the system (admin view)
    @GetMapping
    public List<PortfolioLossAlertResponseDTO> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    // Triggers evaluation of all alerts (manual run)
    @PostMapping("/evaluate")
    public void evaluateAlerts() {
        alertService.evaluateAlertTrigger();
    }
}
