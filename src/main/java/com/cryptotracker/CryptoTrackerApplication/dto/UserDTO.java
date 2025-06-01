package com.cryptotracker.CryptoTrackerApplication.dto;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long userId;
	
	@NotNull(message="Name cannot be null")
	@Pattern( regexp = "^[A-Za-z\\s]{2,50}$", message = "Name must be 2-50 characters long and contain only letters and spaces")
    private String name;
	
	@NotNull(message="Email cannot be null")
	@Email(message="Email should be valid")
	private String email;
	
	
	@Enumerated(EnumType.STRING)
	private Role role;
}