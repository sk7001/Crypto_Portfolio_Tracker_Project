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
    private PortfolioLossAlertService alertService;

    // Create new alert
    @PostMapping
    public PortfolioLossAlertResponseDTO createAlert(@RequestBody PortfolioLossAlertRequestDTO request) {
        return alertService.createAlert(request);
    }

    // Get alerts for specific user
    @GetMapping("/user/{userId}")
    public List<PortfolioLossAlertResponseDTO> getAlertsByUser(@PathVariable Long userId) {
        return alertService.getAlertsByUserId(userId);
    }

    // Get all alerts (admin view)
    @GetMapping
    public List<PortfolioLossAlertResponseDTO> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    // Manual trigger evaluation
    @PostMapping("/evaluate")
    public void evaluateAlerts() {
        alertService.evaluateAlertTrigger();
    }
}
