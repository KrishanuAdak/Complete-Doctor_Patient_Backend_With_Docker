package com.example.demo1.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;



@Entity
@Table(name="Doctor_approval_mapping")
public class Doctor_Approval_Mapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int doctor_id;
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
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public Doctor_Approval_Mapping(int id, int doctor_id, boolean lock_version) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.lock_version = lock_version;
	}
	public Doctor_Approval_Mapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	

}
