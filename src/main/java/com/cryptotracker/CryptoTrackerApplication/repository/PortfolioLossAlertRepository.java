package com.cryptotracker.CryptoTrackerApplication.repository;

//import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;

//import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioLossAlertRepository extends JpaRepository<PortfolioLossAlert, Long> {

	List<PortfolioLossAlert> findByUserId(Long userId);
    // Add custom queries if needed
	
	
}
