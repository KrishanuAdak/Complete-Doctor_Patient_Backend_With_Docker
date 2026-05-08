package com.example.demo1.model;

public class Doctor {
	private String doctor_name;
	private String email;
	private String phone_number;
	public Doctor(String doctor_name, String email, String phone_number) {
		super();
		this.doctor_name = doctor_name;
		this.email = email;
		this.phone_number = phone_number;
	}
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	

}
