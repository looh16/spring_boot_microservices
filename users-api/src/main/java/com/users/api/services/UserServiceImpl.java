package com.users.api.services;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.users.api.data.UserEntity;
import com.users.api.data.UserRepository;
import com.users.api.shared.UserDTO;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		userDTO.setUserId(UUID.randomUUID().toString());
		userDTO.setEncryptedPassord(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		
		userRepository.save(userEntity);
		
		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
		 
		 return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassord(),
				true,true,true,true, new ArrayList<>());
	}
	
	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity = this.userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new ModelMapper().map(userEntity, UserDTO.class);
	}


}
