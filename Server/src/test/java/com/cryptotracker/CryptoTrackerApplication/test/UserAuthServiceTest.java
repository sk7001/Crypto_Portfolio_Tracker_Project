package com.cryptotracker.CryptoTrackerApplication.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cryptotracker.CryptoTrackerApplication.dto.UserAuthDTO;
import com.cryptotracker.CryptoTrackerApplication.dto.UserLoginDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;
import com.cryptotracker.CryptoTrackerApplication.service.UserAuthServiceImpl;
import com.cryptotracker.CryptoTrackerApplication.util.PasswordEncoderUtility;


class UserAuthServiceTest {
	
	@InjectMocks
	private UserAuthServiceImpl userAuthService;
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private PasswordEncoderUtility encoderutility;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void userRegistration() {
		
		User user = new User(1L,"Swetha","swetha123@gmail.com","sw_e28SP",Role.USER);
		//if user exists then no proceeding..if not proceed
		when(userRepo.existsByEmail(user.getEmail())).thenReturn(false);
		when(encoderutility.encodeMyRawPassword("sw_e28SP")).thenReturn("encodedsw_e28SP");
		
		UserAuthDTO response = userAuthService.registerUser(user);
		assertEquals("swetha123@gmail.com",response.getEmail());
		assertEquals("Swetha",response.getName());
		assertEquals("**********",response.getPassword());
		assertEquals(Role.USER,response.getRole());
		verify(userRepo, times(1)).save(any(User.class));
	}
	
	@Test
	void userLogin() {
	    UserLoginDTO loginDto = new UserLoginDTO("swetha123@gmail.com", "sw_e28SP");
	    User userInDB = new User(1L, "Swetha", "swetha123@gmail.com", "encodedsw_e28SP", Role.USER);

	    when(userRepo.findByEmail("swetha123@gmail.com")).thenReturn(java.util.Optional.of(userInDB));
	    when(encoderutility.matchMyPasswords("sw_e28SP", "encodedsw_e28SP")).thenReturn(true);

	    Boolean response = userAuthService.loginUser(loginDto);
	    assertTrue(response);

	    verify(userRepo, times(1)).findByEmail("swetha123@gmail.com");
	    verify(encoderutility, times(1)).matchMyPasswords("sw_e28SP", "encodedsw_e28SP");
	}
  
	  @Test
	    void testLoginUser_UserNotFound_ThrowsException() {
	        UserLoginDTO loginDTO = new UserLoginDTO("notfound@example.com", "password");

	        when(userRepo.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            userAuthService.loginUser(loginDTO);
	        });

	        assertEquals("User not found with email: notfound@example.com", exception.getMessage());
	    }
	

}