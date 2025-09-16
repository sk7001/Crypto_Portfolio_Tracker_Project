package com.cryptotracker.CryptoTrackerApplication.test;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import com.cryptotracker.CryptoTrackerApplication.service.CryptoPriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoPriceServiceImplTest {

    @Mock
    private CryptoPriceRepository priceRepo;

    @InjectMocks
    private CryptoPriceServiceImpl priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSimulatePrices() {
        priceService.simulatePrices();
        verify(priceRepo, times(1)).saveAll(anyList());
    }

    @Test
    public void testGetAllPrices() {
        List<CryptoPrice> mockPrices = List.of(
                new CryptoPrice("BTC", 27000.0, LocalDateTime.now()),
                new CryptoPrice("ETH", 1900.0, LocalDateTime.now())
        );
        when(priceRepo.findAll()).thenReturn(mockPrices);

        List<CryptoPrice> result = priceService.getAllPrices();

        assertEquals(2, result.size());
        assertEquals("BTC", result.get(0).getSymbol());
    }

    @Test
    public void testGetPrice() {
        CryptoPrice mockPrice = new CryptoPrice("DOGE", 0.08, LocalDateTime.now());
        when(priceRepo.findById("DOGE")).thenReturn(Optional.of(mockPrice));

        CryptoPrice result = priceService.getPrice("DOGE");

        assertNotNull(result);
        assertEquals("DOGE", result.getSymbol());
    }
}