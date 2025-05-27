package com.cryptotracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alerts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String symbol;
	private Double triggerPrice;
	private String direction;
	private String status;
	private LocalDateTime triggeredAt;
}

// I have created the Alerts Entity class with all the attributes required to create alerts for the user.