package com.cryptotracker.CryptoTrackerApplication.service;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CryptoPriceServiceImpl implements CryptoPriceService {
	
	/*It automatically injects the CryptoPriceRepository dependency so the service can interact
	 * with the database.
	 */

    @Autowired
    private CryptoPriceRepository priceRepo;

    private static final Map<String, Double> basePrices = Map.of(
        "BTC", 25000.0,
        "ETH", 1800.0,
        "DOGE", 0.07
    );
    
    //It is used to generate prices randomly every 60 seconds(60000 milliseconds)

    @Scheduled(fixedRate = 60000)
    public void simulatePrices() {
    	System.out.println("Simulating prices..."); 
        List<CryptoPrice> prices = new ArrayList<>();
        Random rand = new Random();

        for (String symbol : basePrices.keySet()) {
            double base = basePrices.get(symbol);
            double variation = (rand.nextDouble() - 0.5) * 0.1;
            double newPrice = base + (base * variation);

            CryptoPrice price = new CryptoPrice(symbol, Math.round(newPrice * 100.0) / 100.0, LocalDateTime.now());
            prices.add(price);
        }
        System.out.println(prices);
        priceRepo.saveAll(prices);
    }
    
    //It returns a list of all CryptoPrice records from the database
    
    public List<CryptoPrice> getAllPrices(){
    	return priceRepo.findAll();
    }
    
    //It returns the CryptoPrice records for a specified symbol
    
    public CryptoPrice getPrice(String symbol) {
    	return priceRepo.findById(symbol.toUpperCase())
    					.orElse(null);
    }

   
}

