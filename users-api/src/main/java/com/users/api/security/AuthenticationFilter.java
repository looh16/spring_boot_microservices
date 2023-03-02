package com.users.api.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.api.services.IUserService;
import com.users.api.shared.UserDTO;
import com.users.api.ui.model.LoginRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private IUserService userService;
	
	public AuthenticationFilter(IUserService userService, Environment environment, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.userService = userService;
		this.environment = environment;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();
		UserDTO userDetails = this.userService.getUserDetailsByEmail(userName);
		//String tokenSecret = environment.getProperty("token.secret");
		String tokenSecret= Base64.getEncoder().encodeToString(environment.getProperty("token.secret").getBytes(StandardCharsets.UTF_8));

		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

		Instant now = Instant.now();

		String token = Jwts.builder().setSubject(userDetails.getUserId())
				.setExpiration(
						Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
				.setIssuedAt(Date.from(now)).signWith(secretKey, SignatureAlgorithm.HS512).compact();

		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId());
	}

}