package com.example.demo1.model;

public class AuthDetails {
	private String email;
	private String password;
	private String role;
	private int identity_id;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getIdentity_id() {
		return identity_id;
	}
	public void setIdentity_id(int identity_id) {
		this.identity_id = identity_id;
	}
	public AuthDetails(String email, String password, String role, int identity_id) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.identity_id = identity_id;
	}
	public AuthDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
