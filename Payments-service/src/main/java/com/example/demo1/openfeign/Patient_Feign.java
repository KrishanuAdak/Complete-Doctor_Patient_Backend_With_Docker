package com.example.demo1.openfeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo1.model.PatientDetailsToNotify;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="patients-service")
public interface Patient_Feign {
	@GetMapping("/patients/v1/patient/{id}")
    public PatientDetailsToNotify getPatientNameById(@PathVariable int id);
    
}
