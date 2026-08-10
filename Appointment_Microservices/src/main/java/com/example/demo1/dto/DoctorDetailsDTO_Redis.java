package com.example.demo1.dto;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
public class DoctorDetailsDTO_Redis implements Serializable{
    @JsonProperty("doctorName")
    private String doctor_Name;
    @JsonProperty("phoneNumber")
    private String phone_Number;
    @JsonProperty("city")
    private String city;
    @JsonProperty("experience")
    private int experience;
    private String speclization;
    
    
    public DoctorDetailsDTO_Redis(String doctor_Name, String phone_Number, String city, int experience,
            String speclization) {
        this.doctor_Name = doctor_Name;
        this.phone_Number = phone_Number;
        this.city = city;
        this.experience = experience;
        this.speclization = speclization;
    }
    
    public DoctorDetailsDTO_Redis() {
    }

    public String getDoctor_Name() {
        return doctor_Name;
    }
    public void setDoctor_Name(String doctor_Name) {
        this.doctor_Name = doctor_Name;
    }
    public String getPhone_Number() {
        return phone_Number;
    }
    public void setPhone_Number(String phone_Number) {
        this.phone_Number = phone_Number;
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
}