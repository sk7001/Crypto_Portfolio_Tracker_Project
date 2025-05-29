package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for accessing ProfitAndLoss entities from the database
public interface ProfitAndLossRepository extends JpaRepository<ProfitAndLoss, Long> {
    // Inherits basic CRUD and query operations from JpaRepository
}