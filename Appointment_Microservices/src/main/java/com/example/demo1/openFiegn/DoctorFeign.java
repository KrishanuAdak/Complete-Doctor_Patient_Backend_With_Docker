package com.example.demo1.openFiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo1.dto.DoctorDetailsDTO_Redis;
import com.example.demo1.model.DoctorDetailsToAppointment;

@FeignClient(name="doctor-service")
public interface DoctorFeign {
	
	@GetMapping("/doctor/check/{id}")
	public DoctorDetailsToAppointment sendDoctorDetailsToAppointment(@PathVariable("id") long id);
	@GetMapping("/doctor/fetch/list")
	public List<DoctorDetailsDTO_Redis> getAllDoctorsByCityAndExperience(@RequestParam("city") String city, @RequestParam("experience") int experience);

}
