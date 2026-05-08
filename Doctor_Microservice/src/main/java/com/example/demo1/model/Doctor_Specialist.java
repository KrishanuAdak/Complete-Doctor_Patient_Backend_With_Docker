package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;


@Entity
@Table(name="Doctor_specialist")
public class Doctor_Specialist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int doctor_id;
	private int disease_id;
	private boolean lock_version;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getDisease_id() {
		return disease_id;
	}
	public void setDisease_id(int disease_id) {
		this.disease_id = disease_id;
	}
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public Doctor_Specialist(int id, int doctor_id, int disease_id, boolean lock_version) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.disease_id = disease_id;
		this.lock_version = lock_version;
	}
	public Doctor_Specialist() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
