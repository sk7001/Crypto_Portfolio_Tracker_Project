package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository to access profit_and_loss table
public interface ProfitAndLossRepository extends JpaRepository<ProfitAndLoss, Long> {
    Optional<ProfitAndLoss> findByUserId(Long userId);
    List<ProfitAndLoss> findByUserId(Long userId);
}