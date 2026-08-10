package com.example.demo1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo1.dto.DoctorScheduleRequest;
import com.example.demo1.dto.DoctorScheduleResponse;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorSchedule;
import com.example.demo1.repo.DoctorRepo;
import com.example.demo1.repo.DoctorScheduleRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorScheduleService {
        private final DoctorScheduleRepo scheduleRepository;
    private final DoctorRepo doctorRepository;

    /**
     * Save or update schedule for a doctor.
     * If schedule already exists for that day → update it.
     * If not → create new.
     */
    @Transactional
    public DoctorScheduleResponse saveOrUpdateSchedule(
            Long authUserId,                  // from JWT
            DoctorScheduleRequest request) {

        log.info("Saving schedule for authUserId={}, day={}",
            authUserId, request.getDayOfWeek());

        // Step 1: validate request
        validateRequest(request);

        // Step 2: find doctor by authUserId
        Doctor doctor = doctorRepository
            .findByAuthUserId(authUserId)
            .orElseThrow(() -> 
                new RuntimeException("Doctor not found for authUserId: " 
                    + authUserId));

        // Step 3: check if doctor is approved
        if (!doctor.isRegistrationVerified()) {
            throw new RuntimeException(
                "Your profile is not approved yet. " +
                "You cannot set availability until admin approves you.");
        }

        // Step 4: check if schedule already exists for this day
        // if yes → update, if no → create new
        DoctorSchedule schedule = scheduleRepository
            .findByDoctorIdAndDayOfWeek(
                doctor.getId(), 
                request.getDayOfWeek())
            .map(existing -> {
                // UPDATE existing schedule
                log.info("Updating existing schedule id={}", existing.getId());
                existing.setStartTime(request.getStartTime());
                existing.setEndTime(request.getEndTime());
                existing.setSlotDurationMins(request.getSlotDurationMins());
                existing.setActive(true);
                return existing;
            })
            .orElseGet(() -> {
                // CREATE new schedule
                log.info("Creating new schedule for day={}", 
                    request.getDayOfWeek());
                    System.out.println("save"+ doctor.getAuth_user_id());
                return DoctorSchedule.builder()
                    .doctor(doctor)
                    .dayOfWeek(request.getDayOfWeek())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .slotDurationMins(request.getSlotDurationMins())
                    .build();
            });

        DoctorSchedule saved = scheduleRepository.save(schedule);
        log.info("Schedule saved successfully id={}", saved.getId());

        return DoctorScheduleResponse.builder()
            // .id(saved.getId())
            .dayOfWeek(saved.getDayOfWeek())
            .startTime(saved.getStartTime())
            .endTime(saved.getEndTime())
            .slotDurationMins(saved.getSlotDurationMins())
            .isActive(saved.isActive())
            .message("Schedule saved successfully")
            .build();
    }

    /**
     * Get all active schedules for a doctor.
     * Used by patient to know which days doctor is available.
     */
    public List<DoctorScheduleResponse> getSchedules(Long doctorId) {
        Optional<Doctor> doctorDetails=this.doctorRepository.findByAuthUserId(doctorId);
        log.info("Fetching schedules for doctorId={}", doctorId);

        return scheduleRepository
            .findByDoctorIdAndIsActiveTrue(doctorDetails.get().getId())
            .stream()
            .map(s -> DoctorScheduleResponse.builder()
                .dayOfWeek(s.getDayOfWeek())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .slotDurationMins(s.getSlotDurationMins())
                .message("Available Now")
                .isActive(s.isActive())
                .build())
            .toList();
    }

    /**
     * Deactivate a schedule for a specific day.
     * Doctor says "I won't work Mondays anymore"
     */
    @Transactional
    public DoctorScheduleResponse deactivateSchedule(
            Long authUserId,
            Long scheduleId) {

        log.info("Deactivating scheduleId={}", scheduleId);

        Doctor doctor = doctorRepository
            .findByAuthUserId(authUserId)
            .orElseThrow(() -> 
                new RuntimeException("Doctor not found"));

        DoctorSchedule schedule = scheduleRepository
            .findById(scheduleId)
            .orElseThrow(() -> 
                new RuntimeException("Schedule not found"));

        // make sure this schedule belongs to this doctor
        if (
            schedule.getDoctor().getId()!=(doctor.getId())) {
            throw new RuntimeException(
                "You are not authorized to modify this schedule");
        }

        schedule.setActive(false);
        scheduleRepository.save(schedule);

        log.info("Schedule deactivated id={}", scheduleId);

        return DoctorScheduleResponse.builder()
            // .id(schedule.getId())
            .dayOfWeek(schedule.getDayOfWeek())
            .isActive(false)
            .message("Schedule deactivated successfully")
            .build();
    }

    // ── Validation ───────────────────────────────────
    private void validateRequest(DoctorScheduleRequest request) {

        if (request.getDayOfWeek() == null) {
            throw new RuntimeException("Day of week is required");
        }

        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new RuntimeException("Start time and end time are required");
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new RuntimeException(
                "End time must be after start time");
        }

        if (request.getSlotDurationMins() <= 0) {
            throw new RuntimeException(
                "Slot duration must be greater than 0");
        }

        // minimum session should be at least 1 slot long
        long totalMins = java.time.Duration.between(
            request.getStartTime(), 
            request.getEndTime()).toMinutes();

        if (totalMins < request.getSlotDurationMins()) {
            throw new RuntimeException(
                "Time range is too small for even one slot. " +
                "Increase the time range or reduce slot duration.");
        }
    }

    

}
