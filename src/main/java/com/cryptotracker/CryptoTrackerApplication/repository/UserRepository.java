package com.cryptotracker.CryptoTrackerApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptotracker.CryptoTrackerApplication.entity.User;

public interface UserRepository  extends JpaRepository<User,Long>{
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);


}