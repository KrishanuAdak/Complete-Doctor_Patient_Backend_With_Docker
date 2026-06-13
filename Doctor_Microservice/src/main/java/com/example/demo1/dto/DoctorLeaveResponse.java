package com.example.demo1.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class DoctorLeaveResponse {
    private Long id;
    private LocalDate leaveDate;
    private String reason;
    private String message;
}

