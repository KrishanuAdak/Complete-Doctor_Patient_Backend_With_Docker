package com.example.demo1.controller;




import com.example.demo1.dto.DoctorSlotResponse;
import com.example.demo1.service.DoctorSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class DoctorSlotController {

    private final DoctorSlotService slotService;

    /**
     * GET /api/slots/{doctorId}?date=2024-06-14
     * Patient views available slots for a doctor on a date.
     * Also called by @Tool at RAG query time.
     */
    @GetMapping("/{doctorId}")
    public ResponseEntity<List<DoctorSlotResponse>> getAvailableSlots(
            @PathVariable Long doctorId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
            slotService.getAvailableSlots(doctorId, date));
    }

    /**
     * POST /api/slots/{slotId}/book?appointmentId=101
     * Called by appointment-service via OpenFeign
     * when patient confirms booking.
     */
    @PostMapping("/{slotId}/book")
    public ResponseEntity<DoctorSlotResponse> bookSlot(
            @PathVariable Long slotId,
            @RequestParam Long appointmentId) {

        return ResponseEntity.ok(
            slotService.bookSlot(slotId, appointmentId));
    }

    /**
     * POST /api/slots/release?appointmentId=101
     * Called by appointment-service via OpenFeign
     * when appointment is cancelled.
     */
    @PostMapping("/release")
    public ResponseEntity<DoctorSlotResponse> releaseSlot(
            @RequestParam Long appointmentId) {

        return ResponseEntity.ok(
            slotService.releaseSlot(appointmentId));
    }

    /**
     * PATCH /api/slots/{slotId}/block
     * Doctor manually blocks a slot.
     */
    @PatchMapping("/{slotId}/block")
    public ResponseEntity<DoctorSlotResponse> blockSlot(
            @RequestHeader("X-Auth-User-Id") Long authUserId,
            @PathVariable Long slotId) {

        return ResponseEntity.ok(
            slotService.blockSlot(authUserId, slotId));
    }
}
    

