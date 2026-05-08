package com.example.demo1.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Available_Doctor_Respponse {
	private int id;
	private String doctor_name;
//	   private LocalDateTime start_time;
//	    private LocalDateTime end_time;
	public Available_Doctor_Respponse(int id, String doctor_name) {
		super();
		this.id = id;
		this.doctor_name = doctor_name;
//		this.start_time = start_time;
//		this.end_time = end_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
//	public LocalDateTime getStart_time() {
//		return start_time;
//	}
//	public void setStart_time(LocalDateTime start_time) {
//		this.start_time = start_time;
//	}
//	public LocalDateTime getEnd_time() {
//		return end_time;
//	}
//	public void setEnd_time(LocalDateTime end_time) {
//		this.end_time = end_time;
//	}
	public Available_Doctor_Respponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
