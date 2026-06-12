package com.example.demo1.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;
@Data

public class DoctorScheduleRequest {
    private DayOfWeek dayOfWeek; // MONDAY, TUESDAY etc.
    private LocalTime startTime; // 09:00
    private LocalTime endTime; // 13:00
    private int slotDurationMins; // 30

}
