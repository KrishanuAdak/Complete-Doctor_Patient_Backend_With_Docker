package com.example.demo1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GeneratedValue

@Entity
@Table(name="available_doctors_now_01")
public class Available_Doctor_Now {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
//	@OneToOne
//	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	private int doctor_id;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
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


	public LocalDateTime getStart_time() {
		return start_time;
	}


	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}


	public LocalDateTime getEnd_time() {
		return end_time;
	}


	public void setEnd_time(LocalDateTime end_time) {
		this.end_time = end_time;
	}


	public boolean isLock_version() {
		return lock_version;
	}


	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}


	public Available_Doctor_Now(int id, int doctor_id, LocalDateTime start_time, LocalDateTime end_time,
			boolean lock_version) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.lock_version = lock_version;
	}


	public Available_Doctor_Now() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	

}
