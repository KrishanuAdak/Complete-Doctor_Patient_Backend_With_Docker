package com.example.demo1.dto;

public class AppointmentStatus_Dto {
    private int doctor_id;
    private String status;
    public int getDoctor_id() {
        return doctor_id;
    }
    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public AppointmentStatus_Dto(int doctor_id, String status) {
        this.doctor_id = doctor_id;
        this.status = status;
    }
    public AppointmentStatus_Dto() {
    }
    


}
