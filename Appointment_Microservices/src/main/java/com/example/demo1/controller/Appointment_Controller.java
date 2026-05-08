package com.example.demo1.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.NotificationResponse.NotificationEvent;
import com.example.demo1.NotificationResponse.Patient_Details_To_Admin;
import com.example.demo1.dto.AppointmentStatus_Dto;
import com.example.demo1.dto.Appointment_Dto;
import com.example.demo1.model.Appointment_book_by_Patient;
import com.example.demo1.openFiegn.PatientsFiegn;
import com.example.demo1.service.Appointment_booked_service;
import com.example.demo1.service.RequestContextDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.krishanu.security.RequestContext;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class Appointment_Controller {

	@Autowired
	private Appointment_booked_service service;

	@Autowired
	private RequestContextDetails requestContextDetails;

	@Autowired(required = true)
	private PatientsFiegn feign;

	@Value("${server.port}")
	private String port;

	@GetMapping("/port")
	public String getPort() {
		System.out.println(requestContextDetails.getUserId() + "user id from appointment controller"
				+ requestContextDetails.getRole() + "role from appointment controller");
		if (requestContextDetails.getUserId() == null || requestContextDetails.getRole() == null) {
			return "not running";
		}
		return "port running on" + port + " Role - " + RequestContext.getRole();

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
	public ResponseEntity<?> bookAppointment(@Valid @RequestBody Appointment_Dto data, BindingResult result) {
		try {
			String patient_id = requestContextDetails.getUserId();
			String role = requestContextDetails.getRole();
			String secretKey = requestContextDetails.getSecretKey();
			System.out.println("Secret Key from appointment controller: " + secretKey);
			System.out.println("Patient ID from appointment controller: " + patient_id);
			System.out.println("Role from appointment controller: " + role);
			if (result.hasErrors()) {
				System.out.println(result.getFieldErrors());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
			}
			if (role.equalsIgnoreCase("patient")
					&& data.getAppointment_scheduled_time() != null && data.getDoctor_id() != 0 && patient_id != null) {

				// Getting the patient details to save in the appointment booking table
				Patient_Details_To_Admin p2 = this.feign.getPatientById(Integer.parseInt(patient_id));

				// Saving Notification Data to trigger notification
				NotificationEvent event = new NotificationEvent();
				event.setAppointment_scheduled(data.getAppointment_scheduled_time());
				event.setPhoneNumber(p2.getPhone_number());

				// Here We are saving appointment table data

				try {
					Appointment_book_by_Patient bookedAppointment_book_by_Patient = this.service.saveAppointment(data,
							patient_id, 1);
					this.service.TriggerKafkaProducer_To_Admin(event, Integer.parseInt(patient_id));
					if (bookedAppointment_book_by_Patient != null) {
						return ResponseEntity.status(HttpStatus.CREATED)
								.body("Congratulations!!!   Appointment Booked on "
										+ data.getAppointment_scheduled_time().toLocalDate());
					} else {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Failed to book appointment. Please try again.");
					}

				} catch (JsonProcessingException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
				}
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Sorry! You don't have permission to book any appointment or you have entered wrong data. Please check and try again.");
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAppointmentByDoctor(@PathVariable("id") int appointment_id,
			@RequestBody AppointmentStatus_Dto dto) {
		try {
			String role = requestContextDetails.getRole();

			if (role.equalsIgnoreCase("doctor") && appointment_id != 0 && dto.getStatus() != null) {
				// return this.service.approveOrRejectAppointmentByDoctor(appointment_id, dto);
				return ResponseEntity.ok(this.service.approveOrRejectAppointmentByDoctor(appointment_id, dto));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Sorry! You don't have permission to update the appointment or you have entered wrong data. Please check and try again.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update.");

		}
	}

}
