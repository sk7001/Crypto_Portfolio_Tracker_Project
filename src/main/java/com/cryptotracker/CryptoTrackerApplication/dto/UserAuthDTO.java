package com.cryptotracker.CryptoTrackerApplication.dto;


import com.cryptotracker.CryptoTrackerApplication.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthDTO {
	
	@NotNull(message="user id cannot be null")
	private Long userId;
	
	@NotNull(message="Give a user name. It shouldnt be empty !!!")
	private String name;
	
	@Email(message = "Invalid email format ! Please enter a valid email")
	@NotBlank(message="Email shouldnt be blank ")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",message="Give a strong and valid passsword ! ")
	@NotBlank(message="Password should not be blank")
	private String password;
	
	
	@NotNull(message="Specify a role")
	private Role role;

}