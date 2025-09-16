package com.cryptotracker.CryptoTrackerApplication.test;

import com.cryptotracker.CryptoTrackerApplication.dto.AlertsDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.*;
import com.cryptotracker.CryptoTrackerApplication.exception.UserNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.repository.AlertsRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.service.AlertsServiceImpl;
import com.cryptotracker.CryptoTrackerApplication.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertsServiceImplTest {

    @Mock
    private AlertsRepository alertsRepository;
    @Mock
    private CryptoPriceRepository cryptoPriceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private AlertsServiceImpl alertsService;

    private AlertsDTO alertsDTO;
    private Alerts alert;
    private User user;

    @BeforeEach
    void setUp() {
        alertsDTO = new AlertsDTO(1L, "BTC", 50000.0, AlertsDirection.Above);
        alert = new Alerts(1L, "BTC", 50000.0, AlertsDirection.Above);
        alert.setStatus(AlertsStatus.PENDING);
        alert.setId(1L);
        user = new User();
        user.setUserId(1L);
        user.setEmail("ksrinivassobhit03@gmail.com");
        user.setRole(Role.USER);
    }

    @Test
    void testAddAlert() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        CryptoPrice price = new CryptoPrice();
        price.setSymbol("BTC");
        price.setPrice(50000.0);
        when(cryptoPriceRepository.findBySymbol("BTC")).thenReturn(Optional.of(price));
        when(alertsRepository.findByUserIdAndSymbolAndTriggerPriceAndDirection(
                1L, "BTC", 50000.0, AlertsDirection.Above)).thenReturn(Optional.empty());
        when(alertsRepository.save(any(Alerts.class))).thenReturn(alert);

        Alerts saved = alertsService.addAlert(alertsDTO);

        assertThat(saved.getSymbol()).isEqualTo("BTC");
        assertThat(saved.getTriggerPrice()).isEqualTo(50000.0);
        assertThat(saved.getDirection()).isEqualTo(AlertsDirection.Above);
        verify(alertsRepository, times(1)).save(any(Alerts.class));
        verify(userRepository, times(1)).findById(1L);
        verify(cryptoPriceRepository, times(1)).findBySymbol("BTC");
        verify(alertsRepository, times(1))
                .findByUserIdAndSymbolAndTriggerPriceAndDirection(1L, "BTC", 50000.0, AlertsDirection.Above);
    }

    @Test
    void testGetAlerts() {
        when(alertsRepository.findByUserId(1L)).thenReturn(Collections.singletonList(alert));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<Alerts> result = alertsService.getAlerts(1L);
        assertThat(result).hasSize(1);
        verify(alertsRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetAllAlerts_AdminAccess() {
        User adminUser = new User();
        adminUser.setUserId(2L);
        adminUser.setRole(Role.ADMIN);

        when(userRepository.findById(2L)).thenReturn(Optional.of(adminUser));
        when(alertsRepository.findAll()).thenReturn(List.of(alert));

        List<Alerts> result = alertsService.getAllAlerts(2L);

        assertThat(result).hasSize(1);
        verify(alertsRepository, times(1)).findAll();
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testGetAllAlerts_NonAdminAccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> alertsService.getAllAlerts(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Only ADMINS can access this endpoint");

        verify(userRepository, times(1)).findById(1L);
        verify(alertsRepository, never()).findAll();
    }

    @Test
    void testGetAllAlerts_UserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alertsService.getAllAlerts(999L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("ADMIN not found");

        verify(userRepository, times(1)).findById(999L);
        verify(alertsRepository, never()).findAll();
    }

    @Test
    void testGetPendingAlerts() {
        when(alertsRepository.findByUserIdAndStatus(1L, AlertsStatus.PENDING))
                .thenReturn(List.of(alert));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<Alerts> result = alertsService.getPendingAlerts(1L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(AlertsStatus.PENDING);
        verify(alertsRepository, times(1)).findByUserIdAndStatus(1L, AlertsStatus.PENDING);
    }

    @Test
    void testGetTriggeredAlerts() {
        alert.setStatus(AlertsStatus.TRIGGERED);
        when(alertsRepository.findByUserIdAndStatus(1L, AlertsStatus.TRIGGERED))
                .thenReturn(List.of(alert));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<Alerts> result = alertsService.getTriggeredAlerts(1L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(AlertsStatus.TRIGGERED);
        verify(alertsRepository, times(1)).findByUserIdAndStatus(1L, AlertsStatus.TRIGGERED);
    }

    @Test
    void testCheckAlerts_AboveTriggers() {
        alert.setStatus(AlertsStatus.PENDING);
        when(alertsRepository.findByStatus(AlertsStatus.PENDING)).thenReturn(List.of(alert));
        CryptoPrice price = new CryptoPrice();
        price.setSymbol("BTC");
        price.setPrice(60000.0);
        when(cryptoPriceRepository.findBySymbol("BTC")).thenReturn(Optional.of(price));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(alertsRepository.save(any(Alerts.class))).thenReturn(alert);

        alertsService.checkAlerts();

        verify(alertsRepository, times(1)).save(any(Alerts.class));
        verify(emailService, atLeast(0)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testCheckAlerts_BelowTriggers() {
        alert.setStatus(AlertsStatus.PENDING);
        alert.setDirection(AlertsDirection.Below);
        alert.setTriggerPrice(50000.0);
        CryptoPrice price = new CryptoPrice();
        price.setSymbol("BTC");
        price.setPrice(40000.0);

        when(alertsRepository.findByStatus(AlertsStatus.PENDING)).thenReturn(List.of(alert));
        when(cryptoPriceRepository.findBySymbol("BTC")).thenReturn(Optional.of(price));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(alertsRepository.save(any(Alerts.class))).thenReturn(alert);

        alertsService.checkAlerts();

        verify(alertsRepository, times(1)).save(any(Alerts.class));
        verify(emailService, atLeast(0)).sendEmail(anyString(), anyString(), anyString());
    }
}
