package com.example.demo1.repo;

import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo1.model.DoctorSchedule;

@Repository
public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule, Long> {
    
    List<DoctorSchedule> findByDoctorId(Long doctorId);

    // get all active schedules for a doctor
    List<DoctorSchedule> findByDoctorIdAndIsActiveTrue(Long doctorId);

    // check if schedule already exists for this doctor + day
    Optional<DoctorSchedule> findByDoctorIdAndDayOfWeek(
            Long doctorId, DayOfWeek dayOfWeek);

    // check existence
    boolean existsByDoctorIdAndDayOfWeek(
            Long doctorId, DayOfWeek dayOfWeek);

}
