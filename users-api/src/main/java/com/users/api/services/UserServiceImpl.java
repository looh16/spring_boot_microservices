package com.users.api.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.api.data.UserEntity;
import com.users.api.data.UserRepository;
import com.users.api.shared.UserDTO;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassord("test");
		
		userRepository.save(userEntity);
		
		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
		 
		 return returnValue;
	}

}
