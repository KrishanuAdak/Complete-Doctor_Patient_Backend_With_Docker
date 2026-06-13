package com.example.demo1.repo;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo1.model.DoctorSlot;
import com.example.demo1.model.SlotStatus;
import java.util.List;
import java.util.Optional;

public interface DoctorSlotRepo extends JpaRepository<DoctorSlot,Long>{
        List<DoctorSlot> findByDoctorIdAndSlotDate(
        Long doctorId, LocalDate slotDate);

    // get only AVAILABLE slots — used by @Tool
    List<DoctorSlot> findByDoctorIdAndSlotDateAndStatus(
        Long doctorId, LocalDate slotDate, SlotStatus status);

    // check if slots already generated for this date
    boolean existsByDoctorIdAndSlotDate(
        Long doctorId, LocalDate slotDate);

    // find a specific slot by doctor + date + time
    Optional<DoctorSlot> findByDoctorIdAndSlotDateAndStartTime(
        Long doctorId, LocalDate slotDate, 
        java.time.LocalTime startTime);

    // find slots linked to a schedule rule
    List<DoctorSlot> findByScheduleId(Long scheduleId);

    // find booked slot by appointmentId
    Optional<DoctorSlot> findByAppointmentId(Long appointmentId);
    


}
