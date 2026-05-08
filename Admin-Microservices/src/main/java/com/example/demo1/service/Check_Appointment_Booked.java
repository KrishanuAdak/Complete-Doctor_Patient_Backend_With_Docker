package com.example.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo1.NotificationResponse.NotificationEvent;
import com.example.demo1.NotificationResponse.Notification_Details_To_Notification_Service;

@Service
public class Check_Appointment_Booked {
	
	@Autowired 
 	private KafkaTemplate<String,Notification_Details_To_Notification_Service> kafkaTemplate;
	
	@KafkaListener(topics = "appointment-booked-by-patients", groupId = "admin-group")
	public void check_appointments(NotificationEvent message) {
		System.out.println(message);
		Notification_Details_To_Notification_Service notification=new Notification_Details_To_Notification_Service();
		notification.setAppointment_scheduled(message.getAppointment_scheduled());
		notification.setPhoneNumber(message.getPhoneNumber());
		notification.setBooking_date(message.getBooking_date());
		notification.setBooking_time(message.getBooking_time());
   	kafkaTemplate.send("appointment-by-patients-notification",notification);
		
		
	}

}
