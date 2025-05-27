package com.cryptotracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO // used in login API endpoint , prevents data exposure
{
	
	private String email;
	private String password;

}
