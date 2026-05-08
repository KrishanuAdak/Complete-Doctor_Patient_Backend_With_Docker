package com.example.demo1.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Appointment_notifications")
public class Notification_Sms {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private String message;
	private LocalDate sms_date;
	private LocalTime sms_time;
	private boolean lock_version;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDate getSms_date() {
		return sms_date;
	}
	public void setSms_date(LocalDate sms_date) {
		this.sms_date = sms_date;
	}
	public LocalTime getSms_time() {
		return sms_time;
	}
	public void setSms_time(LocalTime sms_time) {
		this.sms_time = sms_time;
	}
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public Notification_Sms(int id, int user_id, String message, LocalDate sms_date, LocalTime sms_time,
			boolean lock_version) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.message = message;
		this.sms_date = sms_date;
		this.sms_time = sms_time;
		this.lock_version = lock_version;
	}
	public Notification_Sms() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
