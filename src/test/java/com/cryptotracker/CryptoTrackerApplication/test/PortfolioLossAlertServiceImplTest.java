package com.cryptotracker.CryptoTrackerApplication.test;

import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertRequestDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.PortfolioLossAlertResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.PortfolioLossAlert;
import com.cryptotracker.CryptoTrackerApplication.entity.PriceStatus;
import com.cryptotracker.CryptoTrackerApplication.entity.ProfitAndLoss;
import com.cryptotracker.CryptoTrackerApplication.repository.PortfolioLossAlertRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.ProfitAndLossRepository;
import com.cryptotracker.CryptoTrackerApplication.service.PortfolioLossAlertServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioLossAlertServiceImplTest {

    @Mock
    private PortfolioLossAlertRepository alertRepository;

    @Mock
    private ProfitAndLossRepository pnlRepository;

    @InjectMocks
    private PortfolioLossAlertServiceImpl alertService;

    private PortfolioLossAlert testAlert;
    private PortfolioLossAlertRequestDTO testRequest;

    @BeforeEach
    void setUp() {
        testAlert = new PortfolioLossAlert();
        testAlert.setId(1L);
        testAlert.setUserId(100L);
        testAlert.setLossThresholdPercent(500.0);
        testAlert.setStatus("PENDING");

        testRequest = new PortfolioLossAlertRequestDTO(100L, 500.0);
    }

    @Test
    void createAlert_ValidRequest_ReturnsResponseDTO() {
        when(alertRepository.save(any(PortfolioLossAlert.class))).thenReturn(testAlert);

        PortfolioLossAlertResponseDTO response = alertService.createAlert(testRequest);

        assertNotNull(response);
        assertEquals(100L, response.getUserId());
        assertEquals(500.0, response.getLossThresholdPercent());
        assertEquals("PENDING", response.getStatus());
        verify(alertRepository, times(1)).save(any());
    }

    @Test
    void getAlertsByUserId_ExistingUser_ReturnsDTOList() {
        when(alertRepository.findAllByUserId(100L)).thenReturn(List.of(testAlert));

        List<PortfolioLossAlertResponseDTO> result = alertService.getAlertsByUserId(100L);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
        verify(alertRepository, times(1)).findAllByUserId(100L);
    }

    @Test
    void getAllAlerts_Always_ReturnsAllDTOs() {
        when(alertRepository.findAll()).thenReturn(List.of(testAlert));

        List<PortfolioLossAlertResponseDTO> result = alertService.getAllAlerts();

        assertEquals(1, result.size());
        verify(alertRepository, times(1)).findAll();
    }

    @Test
    void evaluateAlertTrigger_ThresholdMet_UpdatesAlertStatus() {
        PortfolioLossAlert pendingAlert = new PortfolioLossAlert();
        pendingAlert.setUserId(100L);
        pendingAlert.setLossThresholdPercent(500.0);
        pendingAlert.setStatus("PENDING");

        ProfitAndLoss pnl = new ProfitAndLoss();
        pnl.setUserId(100L);
        pnl.setPriceStatus(PriceStatus.LOSS);
        pnl.setTotalPortfolio(-600.0);

        when(alertRepository.findAll()).thenReturn(List.of(pendingAlert));
        when(pnlRepository.findAllByUserId(100L)).thenReturn(List.of(pnl));

        alertService.evaluateAlertTrigger();

        assertEquals("TRIGGERED", pendingAlert.getStatus());
        assertNotNull(pendingAlert.getTriggeredAt());
        verify(alertRepository, times(1)).save(pendingAlert);
    }

    @Test
    void evaluateAlertTrigger_ThresholdNotMet_NoStatusChange() {
        PortfolioLossAlert pendingAlert = new PortfolioLossAlert();
        pendingAlert.setUserId(100L);
        pendingAlert.setLossThresholdPercent(1000.0);
        pendingAlert.setStatus("PENDING");

        ProfitAndLoss pnl = new ProfitAndLoss();
        pnl.setUserId(100L);
        pnl.setPriceStatus(PriceStatus.LOSS);
        pnl.setTotalPortfolio(-500.0);

        when(alertRepository.findAll()).thenReturn(List.of(pendingAlert));
        when(pnlRepository.findAllByUserId(100L)).thenReturn(List.of(pnl));

        alertService.evaluateAlertTrigger();

        assertEquals("PENDING", pendingAlert.getStatus());
        assertNull(pendingAlert.getTriggeredAt());
        verify(alertRepository, never()).save(any());
    }

    @Test
    void evaluateAlertTrigger_NoProfitLossData_NoChanges() {
        when(alertRepository.findAll()).thenReturn(List.of(testAlert));
        when(pnlRepository.findAllByUserId(100L)).thenReturn(Collections.emptyList());

        alertService.evaluateAlertTrigger();

        assertEquals("PENDING", testAlert.getStatus());
        verify(alertRepository, never()).save(any());
    }
}

