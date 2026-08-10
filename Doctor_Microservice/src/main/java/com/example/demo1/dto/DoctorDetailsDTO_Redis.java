package com.example.demo1.dto;

import java.io.Serializable;

public class DoctorDetailsDTO_Redis implements Serializable 
 {
    
    private String doctorName;
    private String phoneNumber;
    private String city;
    private Integer experience;
    private String speclization;
   

    public DoctorDetailsDTO_Redis() {}

    public DoctorDetailsDTO_Redis(String doctorName, String phoneNumber, String city, Integer experience, String speclization) {
        this.doctorName = doctorName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.experience = experience;
        this.speclization = speclization;
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getSpeclization() {
        return speclization;
    }

    public void setSpeclization(String speclization) {
        this.speclization = speclization;
    }

   


}
