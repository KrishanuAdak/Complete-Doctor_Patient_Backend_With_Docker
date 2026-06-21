package com.example.demo1.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Available_Doctors {
	private String doctor_name;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private DayOfWeek availableDayOfWeeks;
	private String city;

	public Available_Doctors(LocalDateTime start_time, LocalDateTime end_time, String doctor_name,DayOfWeek availableDayOfWeek,String city) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.doctor_name = doctor_name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.availableDayOfWeeks=availableDayOfWeek;
		this.city=city;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
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
	public DayOfWeek getAvailanDayOfWeek(){
		return availableDayOfWeeks;
	}
	public void setAvailableDayOfWeek(DayOfWeek days){
		this.availableDayOfWeeks=days;

	}
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city=city;
	}

	public Available_Doctors() {
		super();
	}

}
