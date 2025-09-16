package com.cryptotracker.CryptoTrackerApplication.controller;

import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import com.cryptotracker.CryptoTrackerApplication.service.CryptoHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class CryptoHoldingController {

    @Autowired
    private CryptoHoldingService service;

    
    @PostMapping("/add")
    public ResponseEntity<CryptoHolding> addCrypto(@RequestBody CryptoDTO dto) {
        CryptoHolding saved = service.addCryptoHolding(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public List<CryptoHolding> getByUser(@PathVariable Long userId) {
        return service.getCryptoHoldingsByUserId(userId);
    }

    @GetMapping("/{holdingId}")
    public CryptoHolding getById(@PathVariable Long holdingId) {
        return service.getCryptoHoldingById(holdingId);
    }

    @PutMapping("/{holdingId}")
    public CryptoHolding update(@PathVariable Long holdingId, @RequestBody CryptoDTO dto) {
        return service.updateCryptoHolding(holdingId, dto);
    }

    @DeleteMapping("/{holdingId}")
    public String delete(@PathVariable Long holdingId) {
        service.deleteCryptoHolding(holdingId);
        return "Deleted holding with id: " + holdingId;
    }
    @GetMapping("/all/{userId}")
    public List<CryptoHolding> getAllHoldings(@PathVariable Long userId) {
        return service.getAllCryptoHoldings(userId);
    }
}
/* This is the Controller of my Module Crypto-Holding which deals the HTTP Requests and being categorized into 
 * GET- For Fetching Assets of a user from Repository
 * POST - For Posting the Assets Seen through CryptoPrice Simulation and then store this data in repository 
 * PUT - This method is utilized that user has sold some of his asset so he updates his holding asset
 * DELETE - Used this method to sell overall asset */
