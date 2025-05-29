package com.cryptotracker.CryptoTrackerApplication.repository;


import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {
	Optional<CryptoPrice> findBySymbol(String symbol);
}

//This interface allows us to perform database operations like save,file,delete
//It extends JpaRepository to inherit in-built CRUD Operations