package com.example.demo1.model;

import java.io.Serializable;
import java.time.LocalDateTime;


import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;

@Entity
@Table(name="Doctor_Basic_Details")
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	private int doctor_id;
	@Length(min=2)
	private String doctor_name;
	@Length(min=10,max=10)
	private String phone_number;
	private String registrationNumber;
    @Lob
    @Column(name="registrationFile",columnDefinition = "LONGBLOB")
    private byte[] registrationFile;
    private String fileName;
	private boolean isRegistrationVerified;
	private boolean lock_version;
	private LocalDateTime creation_date;
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
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public byte[] getRegistrationFile() {
		return registrationFile;
	}
	public void setRegistrationFile(byte[] registrationFile) {
		this.registrationFile = registrationFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isRegistrationVerified() {
		return isRegistrationVerified;
	}
	public void setRegistrationVerified(boolean registrationVerified) {
		isRegistrationVerified = registrationVerified;
	}
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public LocalDateTime getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Doctor() {
		super();
	}
	public Doctor(int id, @NotNull int doctor_id, @Length(min = 2) String doctor_name,
			@Length(min = 10, max = 10) String phone_number, String registrationNumber, byte[] registrationFile,
			String fileName,  boolean lock_version, LocalDateTime creation_date, boolean isRegistrationVerified) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.phone_number = phone_number;
		this.registrationNumber = registrationNumber;
		this.registrationFile = registrationFile;
		this.fileName = fileName;
		this.lock_version = lock_version;
		this.creation_date = creation_date;
		this.isRegistrationVerified = isRegistrationVerified;
	} 
	
	

	
		
	
}
