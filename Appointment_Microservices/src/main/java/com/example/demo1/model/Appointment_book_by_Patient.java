package com.example.demo1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    name = "Appointment_booked_by_patients",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "appointment_scheduled_time"})
    }
)
public class Appointment_book_by_Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int patient_id;

    @NotNull
    private int doctor_id;

    private String disease_category;

    private String disease_description;

    private int appointment_status_id;

    @FutureOrPresent(message = "Appointment Date should not be past date")
    private LocalDateTime appointment_scheduled_time;

	private LocalDateTime appointment_booked_time;

    private LocalDateTime approvedOrRejected_At;

    @Version
    private int version;


    



    public int getId() { return id; }

    public int getPatient_id() { return patient_id; }
    public void setPatient_id(int patient_id) { this.patient_id = patient_id; }

    public int getDoctor_id() { return doctor_id; }
    public void setDoctor_id(int doctor_id) { this.doctor_id = doctor_id; }

    public String getDisease_category() { return disease_category; }
    public void setDisease_category(String disease_category) { this.disease_category = disease_category; }

    public String getDisease_description() { return disease_description; }
    public void setDisease_description(String disease_description) { this.disease_description = disease_description; }

    public LocalDateTime getAppointment_scheduled_time() { return appointment_scheduled_time; }
    
	
    public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getAppointment_booked_time() {
		return appointment_booked_time;
	}

	public void setAppointment_booked_time(LocalDateTime appointment_booked_time) {
		this.appointment_booked_time = appointment_booked_time;
	}

	public void setAppointment_scheduled_time(LocalDateTime appointment_scheduled_time) {
        this.appointment_scheduled_time = appointment_scheduled_time;
    }

    public int getAppointment_status_id() { return appointment_status_id; }
    public void setAppointment_status_id(int appointment_status_id) {
        this.appointment_status_id = appointment_status_id;
    }

    public Appointment_book_by_Patient() {}

	public Appointment_book_by_Patient(int id, @NotNull int patient_id, @NotNull int doctor_id, String disease_category,
			String disease_description, int appointment_status_id,
			@FutureOrPresent(message = "Appointment Date should not be past date") LocalDateTime appointment_scheduled_time,
			LocalDateTime appointment_booked_time,LocalDateTime approvedOrRejected_At, int version) {
		this.id = id;
		this.patient_id = patient_id;
		this.doctor_id = doctor_id;
		this.disease_category = disease_category;
		this.disease_description = disease_description;
		this.appointment_status_id = appointment_status_id;
		this.appointment_scheduled_time = appointment_scheduled_time;
		this.appointment_booked_time = appointment_booked_time;
        this.approvedOrRejected_At=approvedOrRejected_At;
        this.version = version;
	}

    public LocalDateTime getApprovedOrRejected_At() {
        return approvedOrRejected_At;
    }

    public void setApprovedOrRejected_At(LocalDateTime approvedOrRejected_At) {
        this.approvedOrRejected_At = approvedOrRejected_At;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
	
}