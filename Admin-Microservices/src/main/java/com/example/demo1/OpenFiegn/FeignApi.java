package com.example.demo1.OpenFiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo1.model.Doctor;

@FeignClient(name="doctor-service")
public interface FeignApi {
	
	@GetMapping("doctor/doctors/list")
	public List<Doctor> getAllDoctors(@RequestHeader("X-User-Id") String userid,
	        @RequestHeader("X-Role") String role);

}
