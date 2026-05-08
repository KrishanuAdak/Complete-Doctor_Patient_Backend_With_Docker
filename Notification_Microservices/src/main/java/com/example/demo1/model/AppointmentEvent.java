package com.example.demo1.model;

public class AppointmentEvent {
	private String eventType;
	private String message;
	private String phone_number;
	public AppointmentEvent(String eventType, String message, String phone_number) {
		super();
		this.eventType = eventType;
		this.message = message;
		this.phone_number = phone_number;
	}
	public AppointmentEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	

}
