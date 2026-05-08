package com.example.demo1.dto;

import java.time.LocalDateTime;

public class Appointment_Dto {
    private int doctor_id;
    private LocalDateTime appointment_scheduled_time;
    private String disease_category;
    private String disease_description;
    public int getDoctor_id() {
        return doctor_id;
    }
    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
    public LocalDateTime getAppointment_scheduled_time() {
        return appointment_scheduled_time;
    }
    public void setAppointment_scheduled_time(LocalDateTime appointment_scheduled_time) {
        this.appointment_scheduled_time = appointment_scheduled_time;
    }
    public String getDisease_category() {
        return disease_category;
    }
    public void setDisease_category(String disease_category) {
        this.disease_category = disease_category;
    }
    public String getDisease_description() {
        return disease_description;
    }
    public void setDisease_description(String disease_description) {
        this.disease_description = disease_description;
    }
    public Appointment_Dto(int doctor_id, LocalDateTime appointment_scheduled_time, String disease_category,
            String disease_description) {
        this.doctor_id = doctor_id;
        this.appointment_scheduled_time = appointment_scheduled_time;
        this.disease_category = disease_category;
        this.disease_description = disease_description;
    }
    public Appointment_Dto() {
    }
    



}
