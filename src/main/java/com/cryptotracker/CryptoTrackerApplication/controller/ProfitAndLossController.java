package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossRequest;
import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponse;
import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import com.cryptotracker.CryptoTrackerApplication.service.ProfitAndLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Profit and Loss operations.
 */
@RestController
@RequestMapping("/api/profit-and-loss")
public class ProfitAndLossController {

    // Inject the service layer
    @Autowired
    private ProfitAndLossService service;

    /**
     * Create a new Profit and Loss record.
     * Expects JSON body with 'profit' and 'loss'.
     */
    @PostMapping
    public ProfitAndLoss create(@RequestBody ProfitAndLossRequest request) {
        // Convert request DTO to entity
        ProfitAndLoss entity = new ProfitAndLoss(request.getProfit(), request.getLoss());
        // Persist entity and return the saved object
        return service.save(entity);
    }

    /**
     * Retrieve all Profit and Loss records.
     */
    @GetMapping
    public List<ProfitAndLoss> getAll() {
        return service.findAll();
    }

    /**
     * Retrieve a specific Profit and Loss record by ID.
     */
    @GetMapping("/{id}")
    public ProfitAndLoss getById(@PathVariable Long id) {
        // Get entity by ID or throw an exception if not found
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found for id: " + id));
    }

    /**
     * Delete a Profit and Loss record by ID.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}