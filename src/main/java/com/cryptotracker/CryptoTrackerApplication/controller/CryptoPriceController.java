package com.cryptotracker.CryptoTrackerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.service.CryptoPriceServiceImpl;

//This annotation tells that this class will handle web requests and give JSON responses.

@RestController

//All end points in this controller will start with /price.It acts as a base class for http requests

@RequestMapping("/price")
public class CryptoPriceController {
	
	//It will inject CryptoPriceServiceImpl object automatically
	
	@Autowired
	private CryptoPriceServiceImpl priceService;
	
	//This methods returns all the stored price records from the database
	
	@GetMapping("/all")
	public List<CryptoPrice> getAll(){
		List<CryptoPrice> list= priceService.getAllPrices();
		System.out.println("From Controller: " + list);
		return list;
	}
	
	//This method returns stored price records of the particular symbol from the database
	
	@GetMapping("{symbol}")
	public CryptoPrice getOne(@PathVariable String symbol) {
		return priceService.getPrice(symbol);
	}

}
