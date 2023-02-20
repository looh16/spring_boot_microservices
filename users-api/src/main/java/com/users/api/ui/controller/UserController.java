package com.users.api.ui.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.api.services.IUserService;
import com.users.api.shared.UserDTO;
import com.users.api.ui.model.CreateUserRequest;
import com.users.api.ui.model.CreateUserResponse;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("status/check")
	public String status() {
		return "OK";
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest userDetails)
	{
		ModelMapper modelMapper = new ModelMapper(); 
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDTO userDtO = modelMapper.map(userDetails, UserDTO.class);
		
		UserDTO createdUser = userService.createUser(userDtO);
		
		CreateUserResponse returnValue = modelMapper.map(createdUser, CreateUserResponse.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

}
