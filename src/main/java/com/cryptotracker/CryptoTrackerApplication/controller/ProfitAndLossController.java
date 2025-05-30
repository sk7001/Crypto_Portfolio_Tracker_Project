package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.*;
import com.cryptotracker.CryptoTrackerApplication.service.ProfitAndLossService;
import com.cryptotracker.CryptoTrackerApplication.service.ProfitAndLossServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profit-and-loss")
public class ProfitAndLossController {

    @Autowired
    private ProfitAndLossService pnlService;

    @PostMapping("/calculate/{userId}")
    public ProfitAndLossResponseDTO calculateAndPersist(@PathVariable Long userId) {
        return pnlService.calculateAndPersist(userId);
    }

    @GetMapping("/{userId}")
    public ProfitAndLossResponseDTO getLatest(@PathVariable Long userId) {
        return pnlService.getLatest(userId);
    }
}
/*
I provide APIs to calculate and retrieve a user's profit and loss information.
I delegate all business logic to the service layer for simplicity and clarity.
*/
