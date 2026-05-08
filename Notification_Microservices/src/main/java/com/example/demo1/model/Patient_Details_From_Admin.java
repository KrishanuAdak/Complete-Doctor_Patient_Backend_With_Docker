package com.example.demo1.model;

public class Patient_Details_From_Admin {
	private String eventType;
	private String patient_name;
	private String patient_phone_number;
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	public Patient_Details_From_Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Patient_Details_From_Admin(String eventType,String patient_name, String patient_phone_number) {
		super();
		this.eventType=eventType;
		this.patient_name = patient_name;
		this.patient_phone_number = patient_phone_number;
	}
	

}
