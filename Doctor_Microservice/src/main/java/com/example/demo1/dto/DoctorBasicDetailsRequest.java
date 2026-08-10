package com.example.demo1.dto;

public class DoctorBasicDetailsRequest {
    private String doctor_name;
    private String phone_number;
    private String registrationNumber;
    private String city;
    private String speclization;
    private int experience;
    private String pincode;
    private String fileName;
    private String mimeType;

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
    public DoctorBasicDetailsRequest() {
    }
    public DoctorBasicDetailsRequest(String fileName, String mimeType, String doctor_name, String phone_number,
            String registrationNumber, String city, String speclization, int experience, String pincode) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.doctor_name = doctor_name;
        this.phone_number = phone_number;
        this.registrationNumber = registrationNumber;
        this.city = city;
        this.speclization = speclization;
        this.experience = experience;
        this.pincode = pincode;
    }
    

    


}




