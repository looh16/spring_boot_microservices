package com.users.api.shared;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 5255525867150270161L;

	private String firstName;

	private String lastName;

	private String email;

	private String encryptedPassord;

	private String userId;
	
	private String password;

	public UserDTO() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassord() {
		return encryptedPassord;
	}

	public void setEncryptedPassord(String encryptedPassord) {
		this.encryptedPassord = encryptedPassord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
