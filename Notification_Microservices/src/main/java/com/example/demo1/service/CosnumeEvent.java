package com.example.demo1.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo1.NotificationResponse.AppointmentDetails;
import com.example.demo1.NotificationResponse.Notification_Details_To_Notification_Service;
import com.example.demo1.model.Appointment_Status_Mapping;
import com.example.demo1.model.Notification_Sms;
import com.example.demo1.model.PatientDetailsToNotify;
import com.example.demo1.repo.SmS_Notification_Repo;

@Service
public class CosnumeEvent {
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SmS_Notification_Repo repo;
	
	
	
	@KafkaListener(topics = "appointment-by-patients-notification", groupId = "notification-group-new")
	public void consume(Notification_Details_To_Notification_Service  event) {
	    System.out.println("New appointment : " + event);
	    String message="Your Appointment has been booked on"+event.getAppointment_scheduled()+"Please don't miss out!";
//        this.smsService.sendSms(event.getPhoneNumber(), message);
	    System.out.println("New appointment Sms sent to pateint");

	}
	
	@KafkaListener(topics="check-appointment-by-doctor-approve-reject",groupId="notification-doctor-patient")
	public void notifyDoctorAndPatient(AppointmentDetails details)
	{
		System.out.println(details.getDetails()+ " "+details.getPatient_phonenumber()+" after appointment book -> doctor,patient");
		//String[] name_of_doctor=details.getDetails().split(" ");
		//String doctor_name=(name_of_doctor[2]+" "+name_of_doctor[3]);
		// String messageToDoctor="Hello "+doctor_name+" One Appointment has been booked By "+name_of_doctor[13]+ " "+name_of_doctor[14]+" On "+details.getAppointment_scheduled();
		// String messageToPatient="Hello "+name_of_doctor[13]+" "+name_of_doctor[14]+ "your appointment has been booked. Pending with doctor approval";
	    //this.smsService.sendSms(details.getDoctor_phoneNumber(), messageToDoctor);
        //this.smsService.sendSms(details.getPatient_phonenumber(), messageToPatient);
		System.out.println("Sms sent to Doctor for Appointment approval and patient for pending with doctor approval");
		
		
	}
	@KafkaListener(topics="update-appointment-by-doctor",groupId="notification-after-approve-reject")
	public  void UpdateappointmentByDoctor(Appointment_Status_Mapping message) {
//		this.smsService.sendSms(phone_number, message);
		System.out.println(message.getAppointment_id()+" "+message.getDoctor_id() +" after approve / reject");
		
	}
	@KafkaListener(topics="payment-pending-notification-to-patient",groupId="pending-appointment-payment")
	public  void PendingAppointmentpayment(PatientDetailsToNotify message) {
//		this.smsService.sendSms(phone_number, message);
		System.out.println(message.getPatientName()+" "+message.getPhoneNumber() +" after Payment Initiated / Pending");
		
	}

	public void saveSmsDetails(Notification_Sms sms) {
		Notification_Sms Sms=new Notification_Sms();
		Sms.setMessage(sms.getMessage());
		Sms.setSms_date(LocalDate.now());
		Sms.setSms_time(LocalTime.now());
		Sms.setUser_id(sms.getUser_id());
		this.repo.save(sms);
	}

}
