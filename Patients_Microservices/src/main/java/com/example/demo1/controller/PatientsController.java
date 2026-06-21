package com.example.demo1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.model.Patient_Details_To_Admin;
import com.example.demo1.model.Patients;
import com.example.demo1.service.PatientsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientsController {

	private final PatientsService patientsService;

	public PatientsController(PatientsService patientsService) {
		this.patientsService = patientsService;
	}

	@PostMapping("/save/patient-details")
	public ResponseEntity<?> savePatientDetails(@RequestHeader("X-User-Id") String userId,
			@RequestHeader("X-User-Role") String role, @Valid @RequestBody Patients details, BindingResult result) {
		try {
			if (details != null) {
				System.out.println("Role Controller -"+role);
				long converted_authUserId=Long.parseLong(userId);
				Patients savedDetails = this.patientsService.saveOrUpdatePatientDetails(details, converted_authUserId);
				return ResponseEntity.status(HttpStatus.CREATED).body(savedDetails);
			}
			return ResponseEntity.badRequest().body("Failed to save basic patient details!!");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}

	}

	@GetMapping("/patient/{id}")
	public Patient_Details_To_Admin getPatientsById(@PathVariable("id") int id) {
	return this.patientsService.getPatientById(id);

	}
}
