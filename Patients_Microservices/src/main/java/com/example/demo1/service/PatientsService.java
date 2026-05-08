package com.example.demo1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDetails;
import com.example.demo1.model.Available_Doctor_Now;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.Patients;
import com.example.demo1.openFeign.AuthFeign;
import com.example.demo1.openFeign.OpenFeign;
import com.example.demo1.repo.PatientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Cacheable;

@Service
public class PatientsService {
	
	@Autowired
	private PatientsRepo repo;
	
//	@Autowired
//	private OpenFeign openFiegn;
//	
//	private ObjectMapper mapper;
	
//	@Autowired
//	private PasswordEncoder encoder;
	@Autowired
	private AuthFeign feign;
	
	@Autowired
	private KafkaTemplate<String, Patients> kafkaTemplate;
	
	
	
	
//	public Patients savePatientBasicDetails(Patients p)
//	{
//		
//		Patients pt=p; 
//		Random rand=new Random();
//		int id=rand.nextInt(999999999)+1;
//		pt.setId(id);	
//		pt.setUsername(p.getUsername());
//		pt.setEmail(p.getEmail());
//		pt.setPassword(encoder.encode(p.getPassword()));
//		pt.setPhone_number(p.getPhone_number());
//		pt.setLock_version(false);
//		pt.setRole("USER");
//		pt.setCreation_date(LocalDateTime.now());
//		Patients x=this.repo.save(pt);
//		AuthDetails auth=new AuthDetails();
//		auth.setEmail(p.getEmail());
//		auth.setPassword(encoder.encode(p.getPassword()));
//		auth.setRole(p.getRole());
//		auth.setIdentity_id(id);
//		AuthDetails d=this.feign.savePatientsAuthDetails(auth);		
////		kafkaTemplate.send("new-patients-added",x);		
//		return x;
//	}
	
	
	

	

	public List<Patients> getPatientByName(String name){
		return this.repo.showPatientsByName(name);
		
	}
	
	

}
