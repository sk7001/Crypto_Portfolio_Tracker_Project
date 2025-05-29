package com.cryptotracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.cryptotracker.entity.CryptoPrice;
import com.cryptotracker.repository.AlertsRepository;
import com.cryptotracker.repository.CryptoPriceRepository;
import jakarta.transaction.Transactional;
import com.cryptotracker.dto.AlertsDTO;
import com.cryptotracker.entity.Alerts;
import com.cryptotracker.entity.AlertsDirection;
import com.cryptotracker.entity.AlertsStatus;

@Service
public class AlertsServiceImpl implements AlertsService {

    private static final Logger logger = LoggerFactory.getLogger(AlertsServiceImpl.class);

    @Autowired
    private AlertsRepository alertRepository;

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Override
    public Alerts addAlert(AlertsDTO dto) {
        Alerts alert = new Alerts(dto.getUserId(), dto.getSymbol(), dto.getTriggerPrice(), dto.getDirection());
        return alertRepository.save(alert);
    }

    @Override
    public List<Alerts> getAlerts(Long userID) {
        return alertRepository.findByUserId(userID);
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void checkAlerts() {
        try {
            List<Alerts> alerts = alertRepository.findByStatus(AlertsStatus.PENDING);
            for (Alerts alert : alerts) {
                String symbol = alert.getSymbol();
                Double triggerPrice = alert.getTriggerPrice();
                Optional<CryptoPrice> priceOptional = cryptoPriceRepository.findBySymbol(symbol);
                if (priceOptional.isPresent()) {
                    Double currentPrice = priceOptional.get().getPrice();
                    if (alert.getDirection() == AlertsDirection.Above && currentPrice > triggerPrice) {
                        updateTriggeredAlert(alert);
                    } else if (alert.getDirection() == AlertsDirection.Below && currentPrice < triggerPrice) {
                        updateTriggeredAlert(alert);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in checkAlerts scheduler: ", e);
        }
    }

    private void updateTriggeredAlert(Alerts alert) {
        alert.setStatus(AlertsStatus.TRIGGERED);
        alert.setTriggeredAt(LocalDateTime.now());
        alertRepository.save(alert);
    }

    @Override
    public List<Alerts> getAllAlerts() {
        return alertRepository.findAll();
    }
}
