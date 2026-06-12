package com.example.demo1.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo1.dto.DoctorSlotResponse;
import com.example.demo1.model.*;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo1.repo.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorSlotService {

    private final DoctorSlotRepo slotRepository;
    private final DoctorScheduleRepo scheduleRepository;
    private final DoctorLeaveRepo leaveRepository;
    private final DoctorRepo doctorRepository;

    // ─────────────────────────────────────────────────
    // 1. GET AVAILABLE SLOTS FOR A DATE
    //    Called by patient when they pick a date
    //    Called by @Tool at query time
    // ─────────────────────────────────────────────────
    @Transactional
    public List<DoctorSlotResponse> getAvailableSlots(
            Long doctorId,
            LocalDate date) {

        log.info("Getting available slots: doctorId={}, date={}",
            doctorId, date);

        // Step 1: date cannot be in the past
        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException(
                "Cannot fetch slots for a past date");
        }

        // Step 2: check if doctor is on leave this day
        boolean onLeave = leaveRepository
            .existsByDoctorIdAndLeaveDate(doctorId, date);

        if (onLeave) {
            log.info("Doctor {} is on leave on {}", doctorId, date);
            return List.of(); // empty — no slots available
        }

        // Step 3: check if doctor has a schedule for this day of week
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        DoctorSchedule schedule = scheduleRepository
            .findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek)
            .filter(DoctorSchedule::isActive)
            .orElse(null);

        if (schedule == null) {
            log.info("Doctor {} has no schedule for {}", 
                doctorId, dayOfWeek);
            return List.of(); // doctor doesn't work this day
        }

        // Step 4: generate slots if not already generated for this date
        boolean slotsExist = slotRepository
            .existsByDoctorIdAndSlotDate(doctorId, date);

        if (!slotsExist) {
            log.info("Generating slots for doctorId={}, date={}",
                doctorId, date);
            generateSlots(doctorId, date, schedule);
        }

        // Step 5: return only AVAILABLE slots
        return slotRepository
            .findByDoctorIdAndSlotDateAndStatus(
                doctorId, date, SlotStatus.AVAILABLE)
            .stream()
            .map(slot -> DoctorSlotResponse.builder()
                .slotId(slot.getId())
                .slotDate(slot.getSlotDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .build())
            .toList();
    }

    // ─────────────────────────────────────────────────
    // 2. GENERATE SLOTS FROM SCHEDULE
    //    Internal method — not exposed via API
    //    Reads DoctorSchedule → creates DoctorSlot rows
    // ─────────────────────────────────────────────────
    private void generateSlots(
            Long doctorId,
            LocalDate date,
            DoctorSchedule schedule) {

        Doctor doctor = doctorRepository.findByAuthUserId(doctorId)
            .orElseThrow(() -> 
                new RuntimeException("Doctor not found"));

        List<DoctorSlot> slots = new ArrayList<>();

        LocalTime current = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();
        int durationMins = schedule.getSlotDurationMins();

        // keep creating slots until we reach endTime
        while (current.plusMinutes(durationMins)
                      .compareTo(endTime) <= 0) {

            LocalTime slotEnd = current.plusMinutes(durationMins);

            DoctorSlot slot = DoctorSlot.builder()
                .doctor(doctor)
                .schedule(schedule)
                .slotDate(date)
                .startTime(current)
                .endTime(slotEnd)
                .build();
            // status defaults to AVAILABLE via @Builder.Default

            slots.add(slot);
            current = slotEnd; // move to next slot
        }

        slotRepository.saveAll(slots);

        log.info("Generated {} slots for doctorId={} on {}",
            slots.size(), doctorId, date);
    }

    // ─────────────────────────────────────────────────
    // 3. BOOK A SLOT
    //    Called by appointment-service via OpenFeign
    //    when patient confirms booking
    // ─────────────────────────────────────────────────
    @Transactional
    public DoctorSlotResponse bookSlot(
            Long slotId,
            Long appointmentId) {

        log.info("Booking slotId={}, appointmentId={}",
            slotId, appointmentId);

        DoctorSlot slot = slotRepository.findById(slotId)
            .orElseThrow(() -> 
                new RuntimeException("Slot not found: " + slotId));

        // check slot is still available
        // another patient may have booked it in parallel
        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException(
                "Slot is no longer available. " +
                "Status: " + slot.getStatus() +
                ". Please choose another slot.");
        }

        // mark as BOOKED and link appointmentId
        slot.setStatus(SlotStatus.BOOKED);
        slot.setAppointmentId(appointmentId);
        slotRepository.save(slot);

        log.info("Slot {} booked successfully", slotId);

        return DoctorSlotResponse.builder()
            .slotId(slot.getId())
            .slotDate(slot.getSlotDate())
            .startTime(slot.getStartTime())
            .endTime(slot.getEndTime())
            .status(SlotStatus.BOOKED)
            .message("Slot booked successfully")
            .build();
    }

    // ─────────────────────────────────────────────────
    // 4. RELEASE A SLOT
    //    Called when appointment is cancelled
    //    Slot goes back to AVAILABLE
    // ─────────────────────────────────────────────────
    @Transactional
    public DoctorSlotResponse releaseSlot(Long appointmentId) {

        log.info("Releasing slot for appointmentId={}", appointmentId);

        DoctorSlot slot = slotRepository
            .findByAppointmentId(appointmentId)
            .orElseThrow(() -> 
                new RuntimeException(
                    "No slot found for appointmentId: " 
                    + appointmentId));

        // only release if it was BOOKED
        // ignore if already AVAILABLE or BLOCKED
        if (slot.getStatus() != SlotStatus.BOOKED) {
            throw new RuntimeException(
                "Cannot release slot with status: " 
                + slot.getStatus());
        }

        slot.setStatus(SlotStatus.AVAILABLE);
        slot.setAppointmentId(null); // clear the link
        slotRepository.save(slot);

        log.info("Slot {} released back to AVAILABLE", slot.getId());

        return DoctorSlotResponse.builder()
            .slotId(slot.getId())
            .slotDate(slot.getSlotDate())
            .startTime(slot.getStartTime())
            .endTime(slot.getEndTime())
            .status(SlotStatus.AVAILABLE)
            .message("Slot released successfully")
            .build();
    }

    // ─────────────────────────────────────────────────
    // 5. BLOCK A SLOT MANUALLY
    //    Doctor blocks a specific slot
    //    e.g. lunch break, emergency
    // ─────────────────────────────────────────────────
    @Transactional
    public DoctorSlotResponse blockSlot(
            Long authUserId,
            Long slotId) {

        log.info("Blocking slotId={} by authUserId={}",
            slotId, authUserId);

        Doctor doctor = doctorRepository
            .findByAuthUserId(authUserId)
            .orElseThrow(() -> 
                new RuntimeException("Doctor not found"));

        DoctorSlot slot = slotRepository.findById(slotId)
            .orElseThrow(() -> 
                new RuntimeException("Slot not found"));

        // verify slot belongs to this doctor
        if (slot.getDoctor().getAuth_user_id()!=(doctor.getAuth_user_id()))
            {
            throw new RuntimeException(
                "Not authorized to block this slot");
        }

        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new RuntimeException(
                "Cannot block a slot that is already booked. " +
                "Cancel the appointment first.");
        }

        slot.setStatus(SlotStatus.BLOCKED);
        slotRepository.save(slot);

        log.info("Slot {} blocked", slotId);

        return DoctorSlotResponse.builder()
            .slotId(slot.getId())
            .slotDate(slot.getSlotDate())
            .startTime(slot.getStartTime())
            .endTime(slot.getEndTime())
            .status(SlotStatus.BLOCKED)
            .message("Slot blocked successfully")
            .build();
    }
}
