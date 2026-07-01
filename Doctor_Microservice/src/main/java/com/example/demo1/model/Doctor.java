package com.example.demo1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;

@Entity
@Table(name = "Doctor_Basic_Details")
@EntityListeners(AuditingEntityListener.class)
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private long auth_user_id;
	@Length(min = 2)
	private String doctor_name;
	@Length(min = 10, max = 10)
	private String phone_number;
	private String registrationNumber;
	// Store in s3
	private String registrationFile_Url;
	@NotNull
	private String fileName;
	@BooleanFlag
	private boolean isRegistrationVerified;
	@CreatedDate
	private LocalDateTime creation_date;
	private String city;
	@NotBlank
	private String speclization;
	@Min(1)
	private int experience;
	@NotNull
	private String pincode;
	// optimistic locking for concurrent updates
	@Version
	private Integer version;

	public Doctor() {
	}

	public Doctor(long id, int auth_user_id, String doctor_name, String phone_number, String registrationNumber,
			String registrationFile_Url, String fileName, boolean isRegistrationVerified,
			LocalDateTime creation_date, String city, String speclization, int experience, String pincode,
			Integer version) {
		this.id = id;
		this.auth_user_id = auth_user_id;
		this.doctor_name = doctor_name;
		this.phone_number = phone_number;
		this.registrationNumber = registrationNumber;
		this.registrationFile_Url = registrationFile_Url;
		this.fileName = fileName;
		this.isRegistrationVerified = isRegistrationVerified;
		this.creation_date = creation_date;
		this.city = city;
		this.speclization = speclization;
		this.experience = experience;
		this.pincode = pincode;
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAuth_user_id() {
		return auth_user_id;
	}

	public void setAuth_user_id(long auth_user_id) {
		this.auth_user_id = auth_user_id;
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

	public String getRegistrationFile_Url() {
		return registrationFile_Url;
	}

	public void setRegistrationFile_Url(String registrationFile_Url) {
		this.registrationFile_Url = registrationFile_Url;
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

	public void setRegistrationVerified(boolean isRegistrationVerified) {
		this.isRegistrationVerified = isRegistrationVerified;
	}

	

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSpeclization() {
		return speclization;
	}

	public void setSpeclization(String speclization) {
		this.speclization = speclization;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	

}
