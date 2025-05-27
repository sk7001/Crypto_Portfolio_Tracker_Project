package com.cryptotracker.dto;

import com.cryptotracker.entity.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	
	
	@NotBlank(message="Name is Mandatory")
	@Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Name must be 3-20 characters long and contain only letters, numbers, or underscores")
    private String name;
	
	
	@NotBlank(message = "Email is Mandatory")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "Email should be valid")
	private String email;
	
	 @NotBlank(message = "Password is Mandatory")
	 @Size(min = 6, message = "Password should be at least 6 characters")
	 private String password;
	 
	 @NotBlank(message = "Role is Mandatory")
	 private Role role; //role assigned to the user
	
}
