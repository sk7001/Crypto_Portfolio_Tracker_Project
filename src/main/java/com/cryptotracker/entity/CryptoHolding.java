package com.cryptotracker.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "crypto_holdings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coinName;
    private String symbol;
    private BigDecimal quantityHeld;
    private BigDecimal buyPrice;
    private LocalDate buyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}



