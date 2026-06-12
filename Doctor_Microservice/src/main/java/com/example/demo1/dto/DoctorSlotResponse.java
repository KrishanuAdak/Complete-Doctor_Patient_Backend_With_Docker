package com.example.demo1.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.example.demo1.model.SlotStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorSlotResponse {
    private Long slotId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus status;
    private String message;


}
