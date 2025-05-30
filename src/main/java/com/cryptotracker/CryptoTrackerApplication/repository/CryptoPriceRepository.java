package com.cryptotracker.CryptoTrackerApplication.repository;


import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {
	Optional<CryptoPrice> findBySymbol(String symbol);
	@Query(value = "SELECT * FROM crypto_price WHERE symbol = :symbol", nativeQuery = true)
	CryptoPrice findBySymbol1(@Param("symbol") String symbol);
}
