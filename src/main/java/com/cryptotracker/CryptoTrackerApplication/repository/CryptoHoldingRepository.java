package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CryptoHoldingRepository extends JpaRepository<CryptoHolding, Long> {
    List<CryptoHolding> findByUserId(Long userId);
}
