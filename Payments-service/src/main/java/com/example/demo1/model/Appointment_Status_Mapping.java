package com.example.demo1.model;



public class Appointment_Status_Mapping {
	
	private int id;
	private int appointment_id;
	private int doctor_id;
	private int patient_id;
	private boolean lock_version;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public Appointment_Status_Mapping(int id, int app_id,int doctor_id, int patient_id,
			boolean lock_version) {
		super();
		this.id = id;
		this.appointment_id=app_id;
		this.doctor_id = doctor_id;
		this.patient_id = patient_id;
		this.lock_version = lock_version;
	}
	public Appointment_Status_Mapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	 

}
