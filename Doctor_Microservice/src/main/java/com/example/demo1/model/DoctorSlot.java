package com.example.demo1.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
    name = "doctor_slots",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"doctor_id", "slot_date", "start_time"},
            name = "uk_doctor_date_time"
        )
        // prevents duplicate slot for same doctor + date + time
    }
)
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private DoctorSchedule schedule;

    @Column(nullable = false)
    private LocalDate slotDate;        // 2024-06-17 (specific date)

    @Column(nullable = false)
    private LocalTime startTime;       // 09:00

    @Column(nullable = false)
    private LocalTime endTime;         // 09:30

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SlotStatus status = SlotStatus.AVAILABLE;

    private Long appointmentId;
    // null     → slot is AVAILABLE
    // non-null → slot is BOOKED, value = appointment-service appointment.id

    // ── Timestamps ───────────────────────────────────
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    
}
