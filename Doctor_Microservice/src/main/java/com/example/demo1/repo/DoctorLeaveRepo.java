package com.example.demo1.repo;

import java.time.LocalDate;
import com.example.demo1.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorLeaveRepo extends JpaRepository<Doctor_leaves,Long> {
        boolean existsByDoctorIdAndLeaveDate(
        Long doctorId, LocalDate leaveDate);

    // get all leaves for a doctor
    List<Doctor_leaves> findByDoctorId(Long doctorId);

    // get all future leaves for a doctor
    List<Doctor_leaves> findByDoctorIdAndLeaveDateGreaterThanEqual(
        Long doctorId, LocalDate fromDate);

    // check leave in a date range
    // useful for showing blocked dates in calendar UI
    List<Doctor_leaves> findByDoctorIdAndLeaveDateBetween(
        Long doctorId, LocalDate startDate, LocalDate endDate);


}
