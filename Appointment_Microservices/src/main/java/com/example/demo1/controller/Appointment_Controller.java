package com.example.demo1.controller;

 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo1.dto.Appointment_Dto;
import com.example.demo1.model.Appointment_book_by_Patient;
import com.example.demo1.service.Appointment_booked_service;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class Appointment_Controller {

	private final Appointment_booked_service service;

	@Value("${server.port}")
	private String port;


	public Appointment_Controller(Appointment_booked_service service) {
		this.service = service;
	}
	


	
	@GetMapping("v1/appointments/count")
	public ResponseEntity<?> getAllAppointments() {
		try {
			int counts = this.service.countOfCompletedAppointments();
			return counts > 0 ? ResponseEntity.ok(counts)
					: ResponseEntity.ok(0);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping("/v1/schedule")
	public ResponseEntity<?> bookAppointment(@Valid @RequestBody Appointment_Dto data,
		 BindingResult result,@RequestHeader("X-User-Role") String role,
		 @RequestHeader("X-User-Id") String userId)
{
		try {
			System.out.println("Patient ID from appointment controller: " + userId);
			System.out.println("Role from appointment controller: " + role);
			if (result.hasErrors()) {
				System.out.println(result.getFieldErrors());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
			}			
				try {
					Appointment_book_by_Patient bookedAppointment_book_by_Patient = this.service.saveAppointment(data,
							userId, 1);
					if (bookedAppointment_book_by_Patient != null) {
						return ResponseEntity.status(HttpStatus.CREATED)
								.body("Congratulations!!!   Appointment Booked on "
										+ data.getAppointment_scheduled_time().toLocalDate());
					} else {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Failed to book appointment. Please try again.");
					}

				} catch (JsonProcessingException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some Error occured");
				}
			
		 } catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<?> updateAppointmentByDoctor(@RequestHeader ("X-User-Id") String doctorId,@PathVariable("id") long appointment_id,
			@RequestParam String status) {
		try {
				this.service.approveOrRejectAppointmentByDoctor(appointment_id,status,Long.parseLong(doctorId));
				return ResponseEntity.status(HttpStatus.OK).body("Appointment Status Updated Successfully.");
			}
		 catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update.");

		}
	}

}
