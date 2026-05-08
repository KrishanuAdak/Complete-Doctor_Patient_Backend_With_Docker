package com.example.demo1.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
@Entity
@Table(name="Doctor_Registration_file_mapping")
public class Doctor_Registration_File_Mapping {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id;
	private int Doctor_id;
	@Lob 
	@Column(columnDefinition = "LONGBLOB")
	private byte[] registrationFile;
	private String fileName;
	private String fileType;
	private boolean lock_version;
	private LocalDate created_date;
	private LocalTime created_time;
	public Doctor_Registration_File_Mapping(int id, int doctor_id, byte[] registrationFile, String fileName,
			String fileType, boolean lock_version, LocalDate created_date, LocalTime created_time) {
		super();
		this.id = id;
		Doctor_id = doctor_id;
		this.registrationFile = registrationFile;
		this.fileName = fileName;
		this.fileType = fileType;
		this.lock_version = lock_version;
		this.created_date = created_date;
		this.created_time = created_time;
	}
	public Doctor_Registration_File_Mapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctor_id() {
		return Doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		Doctor_id = doctor_id;
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
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public boolean isLock_version() {
		return lock_version;
	}
	public void setLock_version(boolean lock_version) {
		this.lock_version = lock_version;
	}
	public LocalDate getCreated_date() {
		return created_date;
	}
	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}
	public LocalTime getCreated_time() {
		return created_time;
	}
	public void setCreated_time(LocalTime created_time) {
		this.created_time = created_time;
	}
	

}
