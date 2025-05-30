package com.cryptotracker.CryptoTrackerApplication.test;


import com.cryptotracker.CryptoTrackerApplication.dto.CryptoDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import com.cryptotracker.CryptoTrackerApplication.entity.CryptoPrice;
import com.cryptotracker.CryptoTrackerApplication.exception.CryptoAssetNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.exception.PriceFetchException;
import com.cryptotracker.CryptoTrackerApplication.exception.UserNotFoundException;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoHoldingRepository;
import com.cryptotracker.CryptoTrackerApplication.repository.CryptoPriceRepository;
import com.cryptotracker.CryptoTrackerApplication.service.CryptoHoldingServiceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoHoldingServiceImplTest {

    @Mock
    private CryptoPriceRepository priceRepository;

    @Mock
    private CryptoHoldingRepository repository;

    @InjectMocks
    private CryptoHoldingServiceImpl service;

    private CryptoDTO cryptoDTO;
    private CryptoPrice cryptoPrice;
    private CryptoHolding holding;

    @BeforeEach
    void setUp() {
        cryptoDTO = new CryptoDTO(null, 1L, "Bitcoin", "BTC", 2.5, "2023-10-10");
        cryptoPrice = new CryptoPrice("BTC", 25000.0, LocalDateTime.now());
        holding = new CryptoHolding(1L, 1L, "Bitcoin", "BTC", 2.5, 25000.0, "2023-10-10");
    }

    @Test
    void testAddCryptoHolding_Success() {
        when(priceRepository.findBySymbol1("BTC")).thenReturn(cryptoPrice);
        when(repository.save(any(CryptoHolding.class))).thenReturn(holding);

        CryptoHolding saved = service.addCryptoHolding(cryptoDTO);

        assertNotNull(saved);
        assertEquals(1L, saved.getHoldingId());
        assertEquals("BTC", saved.getSymbol());
        verify(repository).save(any(CryptoHolding.class));
    }

    @Test
    void testGetCryptoHoldingsByUserId_Found() {
        List<CryptoHolding> list = List.of(holding);
        when(repository.findByUserId(1L)).thenReturn(list);

        List<CryptoHolding> result = service.getCryptoHoldingsByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetCryptoHoldingsByUserId_NotFound() {
        when(repository.findByUserId(2L)).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> service.getCryptoHoldingsByUserId(2L));
    }

    @Test
    void testGetCryptoHoldingById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(holding));

        CryptoHolding result = service.getCryptoHoldingById(1L);

        assertEquals("BTC", result.getSymbol());
    }

    @Test
    void testGetCryptoHoldingById_NotFound() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(CryptoAssetNotFoundException.class, () -> service.getCryptoHoldingById(5L));
    }

    @Test
    void testUpdateCryptoHolding_Success() {
        CryptoDTO updateDTO = new CryptoDTO(null, 1L, "Bitcoin Updated", "BTC", 3.0, "2023-12-01");

        when(priceRepository.findBySymbol1("BTC")).thenReturn(cryptoPrice);
        when(repository.findById(1L)).thenReturn(Optional.of(holding));
        when(repository.save(any(CryptoHolding.class))).thenReturn(holding);

        CryptoHolding updated = service.updateCryptoHolding(1L, updateDTO);

        assertEquals("Bitcoin Updated", updated.getCoinName());
        assertEquals(3.0, updated.getQuantityHeld());
    }

    @Test
    void testUpdateCryptoHolding_PriceNotFound() {
        CryptoDTO updateDTO = new CryptoDTO(null, 1L, "Bitcoin", "DOGE", 3.0, "2023-12-01");

        when(priceRepository.findBySymbol1("DOGE")).thenReturn(null);

        assertThrows(PriceFetchException.class, () -> service.updateCryptoHolding(1L, updateDTO));
    }

    @Test
    void testGetAllCryptoHoldings() {
        when(repository.findAll()).thenReturn(List.of(holding));

        List<CryptoHolding> all = service.getAllCryptoHoldings();

        assertEquals(1, all.size());
    }

    @Test
    void testDeleteCryptoHolding_Success() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteCryptoHolding(1L));
    }

    @Test
    void testDeleteCryptoHolding_NotFound() {
        when(repository.existsById(2L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.deleteCryptoHolding(2L));
        assertTrue(thrown.getMessage().contains("Crypto holding not found"));
    }
}

/* Service Layer implementing the business logic in this the 
* first method deals with posting the attributes into repository
* and then second method gets assets based on User id
* third method gets assets based on holding id
* fourth method updates holding by passing object through dto signifying he sold some part of his asset
* and fifth method deletes his asset signifying he sold his asset
* also using of logger to log the operations*/
