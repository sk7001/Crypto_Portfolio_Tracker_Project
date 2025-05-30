package com.cryptotracker.CryptoTrackerApplication.entity;


import lombok.*;
import jakarta.persistence.*;


@Entity
@Table(name = "crypto_holdings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdingId;
    private Long userId;
    private String coinName;
    private String symbol;
    private Double quantityHeld;
    private Double buyPrice;
    private String buyDate;
}
/* Created Entity signifying the attributes used above to save those details in repository and give these through json request
* Except the holding id since it is auto generated*/


