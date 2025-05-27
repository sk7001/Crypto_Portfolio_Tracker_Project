package com.cryptotracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Alerts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	private String symbol;
	private Double triggerPrice;
	@Enumerated(EnumType.STRING)
	private AlertsDirection direction;
	@Enumerated(EnumType.STRING)
	private AlertsStatus status;
	private LocalDateTime triggeredAt;
	
	public Alerts(Long userId, String symbol, Double triggerPrice, AlertsDirection direction, AlertsStatus status) {
	    this.userId = userId;
	    this.symbol = symbol;
	    this.triggerPrice = triggerPrice;
	    this.direction = direction;
	    this.status = status;
	}
}




// I have created the Alerts Entity class with all the attributes required to create alerts for the user.