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
    @GetMapping("/all")
    public List<CryptoHolding> getAll() {
        return service.getAllCryptoHoldings();
    }
}
