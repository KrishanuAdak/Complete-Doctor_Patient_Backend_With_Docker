package com.example.demo1.model;

public class Doctor {
	private int id;
	private String email;
	private String password;
	private String doctor_name;
	private String phone;
	public Doctor(int id, String email, String password, String doctor_name, String phone) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.doctor_name = doctor_name;
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	

}
