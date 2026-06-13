package com.example.demo1.service;




import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo1.dto.DoctorLeaveRequest;
import com.example.demo1.dto.DoctorLeaveResponse;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.Doctor_leaves;
import com.example.demo1.repo.DoctorLeaveRepo;
import com.example.demo1.repo.DoctorRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorLeaveService {

    private final DoctorLeaveRepo leaveRepository;
    private final DoctorRepo doctorRepository;

    // ─────────────────────────────────────────────────
    // 1. APPLY LEAVE
    //    Doctor marks a specific date as leave
    // ─────────────────────────────────────────────────
    @Transactional
    public DoctorLeaveResponse applyLeave(
            Long authUserId,
            DoctorLeaveRequest request) {

        log.info("Applying leave for authUserId={}, date={}",
            authUserId, request.getLeaveDate());

        // Step 1: validate date not in past
        if (request.getLeaveDate().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                "Cannot apply leave for a past date");
        }

        // Step 2: find doctor
        Doctor doctor = doctorRepository
            .findByAuthUserId(authUserId)
            .orElseThrow(() ->
                new RuntimeException("Doctor not found"));

        // Step 3: check leave not already applied for this date
        boolean alreadyOnLeave = leaveRepository
            .existsByDoctorIdAndLeaveDate(
                doctor.getId(), request.getLeaveDate());

        if (alreadyOnLeave) {
            throw new RuntimeException(
                "Leave already applied for: " 
                + request.getLeaveDate());
        }
        return null;
        

        // Step 4: save leave
        // Doctor_leaves leave = Doctor_leaves.builder()
        //     .doctor(doctor)
        //     .leaveDate(request.getLeaveDate())
        //     .reason(request.getReason())
        //     .build();

        // Doctor_leaves saved = leaveRepository.save(leave);
        // log.info("Leave saved id={}", saved.getId());

        // return DoctorLeaveResponse.builder()
        //     .id(saved.getId())
        //     .leaveDate(saved.getLeaveDate())
        //     .reason(saved.getReason())
        //     .message("Leave applied successfully for " 
        //         + saved.getLeaveDate())
        //     .build();
    }

    // ─────────────────────────────────────────────────
    // 2. CANCEL LEAVE
    //    Doctor cancels a previously applied leave
    // ─────────────────────────────────────────────────
    @Transactional
    public DoctorLeaveResponse cancelLeave(
            Long authUserId,
            Long leaveId) {

        log.info("Cancelling leaveId={} by authUserId={}",
            leaveId, authUserId);

        Doctor doctor = doctorRepository
            .findByAuthUserId(authUserId)
            .orElseThrow(() ->
                new RuntimeException("Doctor not found"));

        Doctor_leaves leave = leaveRepository.findById(leaveId)
            .orElseThrow(() ->
                new RuntimeException("Leave not found"));

        // verify this leave belongs to this doctor
        if (leave.getDoctorId()!=(doctor.getId())) {
            throw new RuntimeException(
                "Not authorized to cancel this leave");
        }

        // cannot cancel past leave
        if (leave.getLeaveDate().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                "Cannot cancel a past leave");
        }

        leaveRepository.delete(leave);
        log.info("Leave {} cancelled", leaveId);

        return DoctorLeaveResponse.builder()
            .id(leaveId)
            .leaveDate(leave.getLeaveDate())
            .message("Leave cancelled successfully")
            .build();
    }

    // ─────────────────────────────────────────────────
    // 3. GET ALL UPCOMING LEAVES
    //    Doctor views their future leaves
    //    Also useful for patient calendar UI
    // ─────────────────────────────────────────────────
    public List<DoctorLeaveResponse> getUpcomingLeaves(Long doctorId) {

        log.info("Fetching upcoming leaves for doctorId={}", doctorId);

        return leaveRepository
            .findByDoctorIdAndLeaveDateGreaterThanEqual(
                doctorId, LocalDate.now())
            .stream()
            .map(l -> DoctorLeaveResponse.builder()
                .id(l.getId())
                .leaveDate(l.getLeaveDate())
                .reason(l.getReason())
                .build())
            .toList();
    }

    // ─────────────────────────────────────────────────
    // 4. GET LEAVES IN DATE RANGE
    //    Used by Angular calendar to show blocked dates
    // ─────────────────────────────────────────────────
    public List<DoctorLeaveResponse> getLeavesInRange(
            Long doctorId,
            LocalDate startDate,
            LocalDate endDate) {

        log.info("Fetching leaves for doctorId={} between {} and {}",
            doctorId, startDate, endDate);

        return leaveRepository
            .findByDoctorIdAndLeaveDateBetween(
                doctorId, startDate, endDate)
            .stream()
            .map(l -> DoctorLeaveResponse.builder()
                .id(l.getId())
                .leaveDate(l.getLeaveDate())
                .reason(l.getReason())
                .build())
            .toList();
    }
}


