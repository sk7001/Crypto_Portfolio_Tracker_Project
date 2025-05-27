package com.cryptotracker.service;

import com.cryptotracker.dto.CryptoHoldingRequest;
import com.cryptotracker.dto.CryptoHoldingResponse;
import com.cryptotracker.entity.CryptoHolding;
import com.cryptotracker.entity.User;
import com.cryptotracker.repository.CryptoHoldingRepository;
import com.cryptotracker.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoHoldingServiceImpl implements CryptoHoldingService {

    @Autowired
    private CryptoHoldingRepository holdingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CryptoHoldingResponse addCryptoHolding(Long userId, CryptoHoldingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        CryptoHolding holding = CryptoHolding.builder()
                .user(user)
                .coinName(request.getCoinName())
                .symbol(request.getSymbol())
                .quantityHeld(request.getQuantityHeld())
                .buyPrice(request.getBuyPrice())
                .buyDate(request.getBuyDate())
                .build();

        CryptoHolding saved = holdingRepository.save(holding);

        return mapToResponse(saved);
    }

    @Override
    public List<CryptoHoldingResponse> getHoldingsByUser(Long userId) {
        return holdingRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CryptoHoldingResponse mapToResponse(CryptoHolding holding) {
        return CryptoHoldingResponse.builder()
                .id(holding.getId())
                .userId(holding.getUser().getId())
                .coinName(holding.getCoinName())
                .symbol(holding.getSymbol())
                .quantityHeld(holding.getQuantityHeld())
                .buyPrice(holding.getBuyPrice())
                .buyDate(holding.getBuyDate())
                .build();
    }
}

