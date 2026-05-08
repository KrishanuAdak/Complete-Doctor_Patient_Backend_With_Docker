package com.example.demo1.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo1.model.AuthDetails;

@FeignClient("auth-service")
public interface AuthFeign {
	
	@PostMapping("/auth-db/save")
	public AuthDetails savePatientsAuthDetails(AuthDetails details);
		
	

}
