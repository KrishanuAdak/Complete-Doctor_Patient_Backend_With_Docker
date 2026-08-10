package com.example.demo1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.DoctorBasicDetailsRequest;
import com.example.demo1.dto.DoctorDetailsDTO_Redis;
import com.example.demo1.dto.DoctorBasicDetailsResponse;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.service.DoctorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class BasicController {

	private final DoctorService service;

	public BasicController(DoctorService service) {
		this.service = service;
	}

	@GetMapping("/home")
	public ResponseEntity<?> home() {
		return ResponseEntity.status(HttpStatus.OK).body("welcome to Doctor portal,God");

	}

	@GetMapping("/verified-doctor/counts")
	public int getVerifiedDoctors() {
		return this.service.getAllDoctorsCount();
	}

	@PostMapping("/save-basic-details")
	public ResponseEntity<?> registerDoctor(@RequestHeader("X-User-Role") String role,
			@RequestBody DoctorBasicDetailsRequest d,
			@RequestHeader("X-User-Id") String userId) {
		log.info("controller hitted");
		try {
			if (role.equalsIgnoreCase("doctor")) {
				log.info("--- before saved ---");
				Optional<DoctorBasicDetailsResponse> doctor =this.service.saveOrUpdateBasicDetails(d, Long.parseLong(userId));
				log.info("--- saved ---");
				return doctor.isEmpty() ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save details ")
						: ResponseEntity.status(HttpStatus.OK).body(doctor);
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();

		}

	}

	@GetMapping("/feign/details")
	public long getDoctorByNameAndCity(@RequestParam String name, @RequestParam String city) {
		return this.service.FindIDByDoctorNameAndCityName(name, city);
	}

	@GetMapping("check/{id}")
	public ResponseEntity<?> findByNameAndPhone(@PathVariable int id) {
		DoctorDetailsToAppointment data = this.service.findNameAndPhoneById(id);
		if (data == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	@GetMapping("/fetch/list")
	public ResponseEntity<?> getAllDoctorListByCityAndExperience(@RequestParam(required = false) String city,
			@RequestParam(required = false) int experience) {
		try {
			List<DoctorDetailsDTO_Redis> lists = this.service.getAllDoctorsByCityAndExperience(city, experience);
			if (lists.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body("No Doctors Found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(lists);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());

		}

	}

}
