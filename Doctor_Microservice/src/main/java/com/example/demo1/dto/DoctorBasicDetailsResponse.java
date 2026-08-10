package com.example.demo1.dto;

public class DoctorBasicDetailsResponse {
    private String doctorName;
    private String phoneNumber;
    private String registrationNumber;
     private String city;
    private String speclization;
    private int experience;
    private String pinCode;
    private String documentId;
    private String documentUrl;


    public DoctorBasicDetailsResponse() {}

    public DoctorBasicDetailsResponse(String pinCode,String doctorName, String phoneNumber, String city, String registrationNumber, int experience, String speclization, String documentId, String documentUrl) {
        this.doctorName = doctorName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.registrationNumber = registrationNumber;
        this.experience = experience;
        this.speclization = speclization;
        this.documentId=documentId;
        this.documentUrl=documentUrl;
        this.pinCode=pinCode;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getSpeclization() {
        return speclization;
    }

    public void setSpeclization(String speclization) {
        this.speclization = speclization;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    

}
