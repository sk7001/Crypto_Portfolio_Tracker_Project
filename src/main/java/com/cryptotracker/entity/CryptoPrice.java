package com.cryptotracker.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//Entity is used to mark this class as a database table

@Entity
public class CryptoPrice {
	
	//This annotation is used to specify the primary key
	
	@Id
	private String symbol;
	private double price;
	private LocalDateTime timestamp;
	
	//Getters and setters are used to access variables
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	/*Creating a Default Constructor allows JPA to create object by itself when
	 * loading data from the database*/
	
	public CryptoPrice() {
		
	}
	
	//Creating Constructor for generating objects
	
	public CryptoPrice(String symbol, double price, LocalDateTime timestamp) {
		this.symbol = symbol;
		this.price = price;
		this.timestamp = timestamp;
	}
	
}
