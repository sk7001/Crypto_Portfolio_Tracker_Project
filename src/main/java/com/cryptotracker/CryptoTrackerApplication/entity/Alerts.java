package com.cryptotracker.CryptoPortfolioTrackerApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
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

    public Alerts() {}

    public Alerts(Long userId, String symbol, Double triggerPrice, AlertsDirection direction) {
        this.userId = userId;
        this.symbol = symbol;
        this.triggerPrice = triggerPrice;
        this.direction = direction;
        this.status = AlertsStatus.PENDING;
        this.triggeredAt = LocalDateTime.now();
    }
}




// I have created the Alerts Entity class with all the attributes required to create alerts for the user.

//curl.exe -X POST http://localhost:8080/alerts/ -H "Content-Type: application/json" -d '{"userId": 1, "symbol": "BTC", "triggerPrice": 50000, "direction": "Above"}'
