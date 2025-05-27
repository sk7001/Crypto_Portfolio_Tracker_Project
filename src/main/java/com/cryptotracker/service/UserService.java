package com.cryptotracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cryptotracker.Mappers.UserMapper;
import com.cryptotracker.dto.UserAuthDTO;
import com.cryptotracker.dto.UserRequestDTO;
import com.cryptotracker.dto.UserResponseDTO;
import com.cryptotracker.entity.Role;
import com.cryptotracker.entity.User;
import com.cryptotracker.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private static UserRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//get all users
	public List<UserResponseDTO> getAllUsers()
	{
		return repo
				.findAll()
				.stream()
				.map(UserMapper::EntityTodto)
				.collect(Collectors.toList());
	}
	
	//create new user
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO)
    {
	    User user = UserMapper.dtoToEntity(userRequestDTO);
	    user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
	    User savedUser = repo.save(user);
	    return UserMapper.EntityTodto(savedUser);
	}
    
    //login
    public UserResponseDTO login(UserAuthDTO authDTO)
    {
        User user = repo.findByEmail(authDTO.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid email or password");
        }

        return UserMapper.EntityTodto(user);
    }


	
	//get user by email
	public User getbyEmail(String email)
	{
	    return repo.findByEmail(email).get();
	}
	
	//update the role of user
	public UserResponseDTO updateUserRole(Long userId, Role newRole)
	{
		    User user = repo.findById(userId).orElseThrow();
		    user.setRole(newRole);
		    User updatedUser = repo.save(user);
		    return UserMapper.EntityTodto(updatedUser);
	}

	//delete the user
	public void deleteUser(Long id)
	{
        User user = repo.findById(id).orElseThrow();
        repo.delete(user);
	}
}
