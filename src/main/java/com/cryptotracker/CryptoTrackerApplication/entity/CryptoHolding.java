package com.cryptotracker.CryptoTrackerApplication.entity;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;



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
    private String symbol;
    private Double quantityHeld;
    private Double buyPrice;
    private LocalDateTime buyDate;
}
/* Created Entity signifying the attributes used above to save those details in repository and give these through json request
* Except the holding id since it is auto generated*/


