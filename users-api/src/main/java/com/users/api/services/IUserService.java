package com.users.api.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.users.api.shared.UserDTO;

public interface IUserService extends UserDetailsService{
	
	public UserDTO createUser(UserDTO userDTO);
	
	public UserDTO getUserDetailsByEmail(String email);

	

}
