package com.cryptotracker.CryptoTrackerApplication.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	
	
    //I am creating seperate login dto for user as im only checking login with mail and password
    @Email(message = "Invalid email format ! Please enter a valid email")
    @NotNull(message="Email shouldn't be null ! ")
    private String email;

    @NotBlank(message="Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
    		message="Give a strong and valid passsword ! ")
    private String password;
}