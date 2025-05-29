package com.cryptotracker.CryptoTrackerApplication.repository;


import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {
	Optional<CryptoPrice> findBySymbol(String symbol);
}
