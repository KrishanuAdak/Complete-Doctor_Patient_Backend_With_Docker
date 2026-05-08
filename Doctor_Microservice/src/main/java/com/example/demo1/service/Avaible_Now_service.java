package com.example.demo1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Available_Doctor_Now;
import com.example.demo1.repo.Available_Doctor_Now_Repo;


@Service
public class Avaible_Now_service {
	
	@Autowired
	private Available_Doctor_Now_Repo repo;


	
    @Autowired
    private KafkaTemplate<String,Available_Doctor_Now> kafkaTemplate;
    
//    @PersistenceContext
//    private EntityManager entity;

    
	
	public Available_Doctor_Now add(Available_Doctor_Now dd)
	{
		LocalDateTime currentDateTime=LocalDateTime.now();
		 LocalDateTime newDateTime = currentDateTime.plusMinutes(30);
		Available_Doctor_Now d=dd;
		d.setDoctor_id(dd.getDoctor_id());		
		d.setStart_time(LocalDateTime.now());
		d.setEnd_time(newDateTime);	 
		d.setLock_version(false);	 	
		Available_Doctor_Now x=this.repo.save(d);
//		this.kafkaTemplate.send("available-doc",new Available_Doctor_Now(dd.getId(), dd.getDoctor_id(), LocalDateTime.now(), newDateTime, false));
		System.out.println("Event pushed!"+Available_Doctor_Now.class);
		return x;		
	}
	
	public List<Integer> availableDoctors()
	{
		return this.repo.getAllAvailableDoctors(); 
	}
	
	
	@Scheduled(cron="35 35 23 * * ?")
	public void deletePreviousRecords()
	{
		this.repo.deleteRecords();		
	}
	
	

}
