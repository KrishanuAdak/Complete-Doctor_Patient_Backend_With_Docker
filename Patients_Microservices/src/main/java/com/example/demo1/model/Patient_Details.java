package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name="Patient_Details")
public class Patient_Details {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int patient_id;
	private String patient_name;
	private String patient_phone_number;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPatient_phone_number() {
		return patient_phone_number;
	}
	public void setPatient_phone_number(String patient_phone_number) {
		this.patient_phone_number = patient_phone_number;
	}
	public Patient_Details(int id, int patient_id, String patient_name, String patient_phone_number) {
		super();
		this.id = id;
		this.patient_id = patient_id;
		this.patient_name = patient_name;
		this.patient_phone_number = patient_phone_number;
	}
	public Patient_Details() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
