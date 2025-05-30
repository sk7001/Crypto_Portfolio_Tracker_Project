package com.cryptotracker.CryptoTrackerApplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CryptoDTO {
	private Long holdingId; 
    private Long userId;
    private String coinName;
    private String symbol;
    private Double quantityHeld;
    private String buyDate;
}

/* DTO (Data Transfer Object) for my module Crypto-Holdings which communicates between the server and the client 
* and vice versa since I haven't distinguished My DTO as Request or Response*/