package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.Alerts;
import com.cryptotracker.CryptoTrackerApplication.entity.AlertsDirection;
import com.cryptotracker.CryptoTrackerApplication.entity.AlertsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertsRepository extends JpaRepository<Alerts, Long> {
    List<Alerts> findByUserId(Long userId);
    List<Alerts> findByStatus(AlertsStatus status);
	List<Alerts> findByUserIdAndStatus(Long userId, AlertsStatus pending);
	Optional<Alerts> findByUserIdAndSymbolAndTriggerPriceAndDirection(Long userId, String symbol, Double triggerPrice, AlertsDirection direction);
}


//Created a JPA Repository to save all the alerts in the Database.
