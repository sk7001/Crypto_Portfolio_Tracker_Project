package com.cryptotracker.CryptoTrackerApplication.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoderUtility  {
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}