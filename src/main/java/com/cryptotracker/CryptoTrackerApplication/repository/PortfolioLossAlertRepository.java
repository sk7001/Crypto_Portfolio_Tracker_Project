package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// Repository interface for PortfolioLossAlert entity
public interface PortfolioLossAlertRepository extends JpaRepository<PortfolioLossAlert, Long> {
    Optional<PortfolioLossAlert> findByUserId(Long userId);        // Return a single alert (e.g., latest)
    List<PortfolioLossAlert> findAllByUserId(Long userId);         // Return all alerts for a user
}




