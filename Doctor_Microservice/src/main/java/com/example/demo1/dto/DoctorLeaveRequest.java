package com.example.demo1.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DoctorLeaveRequest {

    private LocalDate leaveDate;
    private String reason;

}
