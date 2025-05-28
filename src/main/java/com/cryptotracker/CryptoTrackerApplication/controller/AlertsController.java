package com.cryptotracker.controller;

import com.cryptotracker.dto.AlertsDTO;
import com.cryptotracker.entity.Alerts;
import com.cryptotracker.service.AlertsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
public class AlertsController {

    private static final Logger logger = LoggerFactory.getLogger(AlertsController.class);

    @Autowired
    private AlertsService alertsService;

    @PostMapping("/")
    public ResponseEntity<Map<String, String>> saveNewAlert(@RequestBody AlertsDTO alertdto) {
        try {
            alertsService.addAlert(alertdto);
            return ResponseEntity.ok(Map.of("message", "Alert created successfully."));
        } catch (Exception e) {
            logger.error("Error creating alert: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unable to create alert: " + e.getMessage()));
        }
    }

    @GetMapping("/getAlerts/{id}")
    public ResponseEntity<?> getAlerts(@PathVariable Long id) {
        try {
            List<Alerts> alertsList = alertsService.getAlerts(id);
            return ResponseEntity.ok(alertsList);
        } catch (Exception e) {
            logger.error("Error fetching alerts for user {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to get alerts for userID " + id));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Alerts>> getAllAlerts() {
        List<Alerts> alertsList = alertsService.getAllAlerts();
        return ResponseEntity.ok(alertsList);
    }
}
