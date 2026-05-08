package com.example.demo1.NotificationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Notification_Details_To_Notification_Service {
	    private String phoneNumber;
	    private LocalDate booking_date;
	    private LocalTime booking_time;
	    private LocalDateTime appointment_scheduled;

	    // Constructors
	    public Notification_Details_To_Notification_Service() {}


		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}



		public LocalDate getBooking_date() {
			return booking_date;
		}

		public void setBooking_date(LocalDate booking_date) {
			this.booking_date = booking_date;
		}

		public LocalTime getBooking_time() {
			return booking_time;
		}

		public void setBooking_time(LocalTime booking_time) {
			this.booking_time = booking_time;
		}

		public LocalDateTime getAppointment_scheduled() {
			return appointment_scheduled;
		}

		public void setAppointment_scheduled(LocalDateTime appointment_scheduled) {
			this.appointment_scheduled = appointment_scheduled;
		}

		public Notification_Details_To_Notification_Service( String phoneNumber, LocalDate booking_date,
				LocalTime booking_time, LocalDateTime appointment_scheduled) {
			super();

			this.phoneNumber = phoneNumber;

			this.booking_date = booking_date;
			this.booking_time = booking_time;
			this.appointment_scheduled = appointment_scheduled;
		}
	   
		


}
