package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repository to access profit_and_loss table
public interface ProfitAndLossRepository extends JpaRepository<ProfitAndLoss, Long> {
    Optional<ProfitAndLoss> findByUserId(Long userId);             // For single latest PnL
    List<ProfitAndLoss> findAllByUserId(Long userId);              // For historical/all PnL entries
}

/*
I provide database access methods for the profit_and_loss table.
I enable easy retrieval and management of ProfitAndLoss records by userId using Spring Data JPA.
*/