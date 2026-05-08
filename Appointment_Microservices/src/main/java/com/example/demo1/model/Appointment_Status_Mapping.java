package com.example.demo1.model;

public class Appointment_Status_Mapping {
    public int doctor_id;
    public String status;
    public int appointment_id;
    public int patient_id;

   public Appointment_Status_Mapping(int appointment_id, int doctor_id, String status) {
       this.appointment_id = appointment_id;
       this.doctor_id = doctor_id;
       this.status = status;
   }
   
   
    
    public Appointment_Status_Mapping() {
}



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
    public int getAppointment_id() {
        return appointment_id;
    }
    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
    public int getPatient_id() {
        return patient_id;
    }
    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

}
