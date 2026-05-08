package com.example.demo1.model;

public class PatientDetailsToNotify {
    private String patientName;
    private String phoneNumber;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PatientDetailsToNotify() {
        super();
    }

    public PatientDetailsToNotify(String patientName, String phoneNumber) {
        super();
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
    }
    
}
