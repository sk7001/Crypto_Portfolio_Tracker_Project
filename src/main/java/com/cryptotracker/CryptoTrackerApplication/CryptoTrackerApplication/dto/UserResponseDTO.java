package com.cryptotracker.CryptoTrackerApplication.CryptoTrackerApplication.dto;

import com.cryptotracker.CryptoTrackerApplication.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	private long id;
	private String name;
	private String email;
	private Role role;
	
	//no password field  to keep it safe

}
 