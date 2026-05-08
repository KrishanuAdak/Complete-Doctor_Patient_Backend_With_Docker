package com.example.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients(basePackages = "com.example.demo1.openFeign")
@EnableDiscoveryClient
public class PatientsMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMicroservicesApplication.class, args);
	}

}

