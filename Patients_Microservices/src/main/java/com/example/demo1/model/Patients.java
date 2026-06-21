package com.example.demo1.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Patients_list_01")
@EntityListeners(AuditingEntityListener.class)
public class Patients {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="auth_user_id")
	private long patient_id;
	private String fullName;
	@Size(min=10,max=10)
	private String phone_number;
	private String city;
	private String pin;
	@CreatedDate
	private LocalDateTime creation_date;

	public Patients() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	

	public long getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(long patient_id) {
		this.patient_id = patient_id;
	}
	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public Patients(long id, @Size(min = 10, max = 10) String phone_number, String city, String pin,
			LocalDateTime creation_date,long patient_id, String fullName) {
		this.id = id;
		this.phone_number = phone_number;
		this.city = city;
		this.pin = pin;
		this.creation_date = creation_date;
		this.patient_id=patient_id;
		this.fullName=fullName;
	}
	
	
	
	
	
	

}
