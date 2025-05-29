package com.cryptotracker.CryptoTrackerApplication.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//Utility class for handling password encoding and matching using BCrypt.
@Component
public class PasswordEncoderUtility  {
	
	//BCryptPasswordEncoder instance used to hash and verify passwords
	private  final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	//Encodes a raw password using BCrypt hashing algorithm.
	public  String encodeMyRawPassword(String password){
		return encoder.encode(password);
	}
	
	//Compares a raw password with an already encoded (stored) password.
	public boolean matchMyPasswords(String raw,String dbStoredPassword){
		
		return encoder.matches(raw,dbStoredPassword);
	
		
}
}