package com.example.demo1.service;

import org.springframework.kafka.annotation.KafkaListener;

import com.example.demo1.model.Registered_User;

public class KafkaServicePatients {
	@KafkaListener(topics = "new-user-registered", groupId = "admin-patient-group")
	public void listen(Registered_User user) {
	    System.out.println("Received User: " + user);
	}


}
