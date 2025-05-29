package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository interface for PortfolioLossAlert entity
public interface PortfolioLossAlertRepository extends JpaRepository<PortfolioLossAlert, Long> {

    // Finds all alerts for a given user
    List<PortfolioLossAlert> findByUserId(Long userId);
}
