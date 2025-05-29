package com.cryptotracker.CryptoTrackerApplication.service;

import java.util.List;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;

public interface CryptoPriceService {
	
	public void simulatePrices();
	public List<CryptoPrice> getAllPrices();
	public CryptoPrice getPrice(String symbol);

}
