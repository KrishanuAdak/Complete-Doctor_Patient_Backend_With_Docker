package com.example.demo1.openFeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo1.model.Available_Doctor_Respponse;
import com.example.demo1.model.Doctor;

@FeignClient(name="doctor-service",url="localhost:8085/api/doctors")
public interface OpenFeign {
	@GetMapping("/doctors/list")
	public List<Doctor> getAllDoctors(); 
	
	@PostMapping("/add/doctor")
	public void saveDoctor(Doctor doctor);
	
	@GetMapping("/available-doctors")
	public List<Available_Doctor_Respponse> getAvailableDoctorsFromPatients();

}
