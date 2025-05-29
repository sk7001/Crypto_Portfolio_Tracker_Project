package com.cryptotracker.CryptoPortfolioTrackerApplication.repository;

import com.cryptotracker.CryptoPortfolioTrackerApplication.entity.Alerts;
import com.cryptotracker.CryptoPortfolioTrackerApplication.entity.AlertsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertsRepository extends JpaRepository<Alerts, Long> {
    List<Alerts> findByUserId(Long userId);
    List<Alerts> findByStatus(AlertsStatus status);
	List<Alerts> findByUserIdAndStatus(Long userId, AlertsStatus pending);
}


//Created a JPA Repository to save all the alerts in the Database.
