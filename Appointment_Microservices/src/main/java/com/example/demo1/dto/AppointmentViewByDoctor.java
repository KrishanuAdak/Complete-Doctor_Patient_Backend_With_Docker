package com.example.demo1.dto;

import java.time.LocalDateTime;

public interface  AppointmentViewByDoctor {
    String getDiseaseType();
    LocalDateTime getAppointmentScheduled();
    String getAppointmentStatus();
    String getPatientName();

 

}
