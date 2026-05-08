package com.example.demo1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Doctor_Login_Records")
public class LoginRecordsDoctor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int doctor_id;
	private LocalDateTime loginTime;
	private LocalDateTime logoutTime;
	private Date loginDate;
	private boolean lock_version;

	
	public LoginRecordsDoctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginRecordsDoctor(int id, int doctor_id, LocalDateTime loginTime, LocalDateTime logoutTime,Date loginDate,
			boolean lock_version) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.loginDate=loginDate;
		this.lock_version = lock_version;
	}

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
	public LocalDateTime getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}
	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	

}
