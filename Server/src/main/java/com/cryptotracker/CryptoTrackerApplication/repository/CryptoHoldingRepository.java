package com.cryptotracker.CryptoTrackerApplication.repository;

import com.cryptotracker.CryptoTrackerApplication.entity.CryptoHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CryptoHoldingRepository extends JpaRepository<CryptoHolding, Long> {
    List<CryptoHolding> findByUserId(Long userId);

    @Query("SELECT DISTINCT c.userId FROM CryptoHolding c")
    List<Long> findAllDistinctUserIds();
 }
/* This is the Repository Interface Using JPA in order to use MySQL to store all the attributes through the DTO*/