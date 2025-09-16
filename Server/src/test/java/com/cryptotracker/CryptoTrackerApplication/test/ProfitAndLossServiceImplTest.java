package com.cryptotracker.CryptoTrackerApplication.test;

import com.cryptotracker.CryptoTrackerApplication.entity.*;
import com.cryptotracker.CryptoTrackerApplication.repository.*;
import com.cryptotracker.CryptoTrackerApplication.dto.ProfitAndLossResponseDTO;
import com.cryptotracker.CryptoTrackerApplication.service.ProfitAndLossServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfitAndLossServiceImplTest {

    @InjectMocks
    private ProfitAndLossServiceImpl pnlService;

    @Mock
    private CryptoHoldingRepository holdingRepo;
    @Mock
    private CryptoPriceRepository priceRepo;
    @Mock
    private ProfitAndLossRepository pnlRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLatest_Found() {
        Long userId = 3L;
        ProfitAndLoss saved = new ProfitAndLoss(7L, userId, PriceStatus.NEUTRAL, 0.0);
        when(pnlRepo.findByUserId(userId)).thenReturn(Optional.of(saved));

        ProfitAndLossResponseDTO resp = pnlService.getLatest(userId);

        assertNotNull(resp, "Should return a response object");
        assertEquals(userId, resp.getUserId());
        assertEquals(0.0, resp.getTotalProfitLoss(), 0.001);
        assertEquals(PriceStatus.NEUTRAL, resp.getPriceStatus());
    }

    @Test
    void testGetLatest_NotFound() {
        when(pnlRepo.findByUserId(anyLong())).thenReturn(Optional.empty());
        assertNull(pnlService.getLatest(999L));
    }
}