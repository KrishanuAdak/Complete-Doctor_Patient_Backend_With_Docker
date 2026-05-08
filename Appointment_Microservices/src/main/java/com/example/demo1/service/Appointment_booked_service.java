package com.example.demo1.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import com.example.demo1.model.Appointment_Status_Mapping;
import com.example.demo1.NotificationResponse.AppointmentDetails;
import com.example.demo1.NotificationResponse.NotificationEvent;
import com.example.demo1.NotificationResponse.Patient_Details_To_Admin;
import com.example.demo1.dto.AppointmentStatus_Dto;
import com.example.demo1.dto.AppointmentViewByDoctor;
import com.example.demo1.dto.Appointment_Dto;
import com.example.demo1.model.Appointment_book_by_Patient;
import com.example.demo1.model.Appointment_outbox_events;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.openFiegn.DoctorFeign;
import com.example.demo1.openFiegn.PatientsFiegn;
import com.example.demo1.repo.AppointmentOutboxRepo;
import com.example.demo1.repo.AppointmentRepo;
import com.example.demo1.repo.Appointment_Status_Repo;
import com.example.demo1.repo.Disease_list_repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Service
public class Appointment_booked_service {

	private final Logger logger = LoggerFactory.getLogger(Appointment_booked_service.class);

	private static final String TOPIC = "appointment-booked-by-patients";

	@Autowired(required = true)
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private AppointmentRepo AppointmentRepo;

	@Autowired
	private Disease_list_repo DiseaseRepo;

	@Autowired
	private PatientsFiegn feign;

	@Autowired
	private Appointment_Status_Repo AppointmentStatusRepo;

	@Autowired
	private DoctorFeign doctorFeign;

	@Autowired
	private AppointmentOutboxRepo outboxRepo;

	@Autowired
	private ObjectMapper objectMapper;


	public int countOfCompletedAppointments() {
		try {
			return this.AppointmentRepo.countOfCompletedAppointments();
		} catch (Exception e) {
			logger.error("Error while counting completed appointments: " + e.getMessage());
			return -1; // Indicating an error occurred
		}
	}

	public void TriggerKafkaProducer_To_Admin(NotificationEvent details, int id) {
		NotificationEvent notification = new NotificationEvent();
		notification.setEventType(TOPIC);
		notification.setMessage("One New Appointment Has Just Booked !");
		notification.setBooking_date(LocalDate.now());
		notification.setAppointment_scheduled(details.getAppointment_scheduled());
		notification.setBooking_time(LocalTime.now());
		notification.setPhoneNumber(details.getPhoneNumber());
		this.kafkaTemplate.send(TOPIC, notification);
	}

	@Transactional
	@CircuitBreaker(name = "appointment_patient_service", fallbackMethod = "handleCircuitBreaker")
	public Appointment_book_by_Patient saveAppointment(Appointment_Dto data, String userid,
			int disease_id) throws JsonProcessingException {
		if (data != null) {
			Appointment_book_by_Patient data1 = new Appointment_book_by_Patient();

			// For Saving Appointment data
			data1.setPatient_id(Integer.parseInt(userid));
			data1.setDoctor_id(data.getDoctor_id());
			data1.setAppointment_scheduled_time(data.getAppointment_scheduled_time());
			String disease_name = this.DiseaseRepo.getDiseaseName(disease_id);
			data1.setDisease_category(disease_name);
			data1.setDisease_description(data.getDisease_description());
			int status_id = this.AppointmentStatusRepo.findIdByStatusName("pending");
			data1.setAppointment_status_id(status_id);
			data1.setAppointment_booked_time(LocalDateTime.now());

			Appointment_book_by_Patient p = this.AppointmentRepo.save(data1);

			// Get Data from open feign for Patient Id
			Patient_Details_To_Admin patient_details = this.feign.getPatientById(Integer.parseInt(userid));

			// Send Message to Patient for notifying
			NotificationEvent event = new NotificationEvent();
			event.setAppointment_scheduled(data.getAppointment_scheduled_time());
			event.setPhoneNumber(patient_details.getPhone_number());
			this.TriggerKafkaProducer_To_Admin(event, Integer.parseInt(userid));
			// Get Doctor Detaild from OpenFeign
			DoctorDetailsToAppointment docDetails = this.doctorFeign.sendDoctorDetailsToAppointment(8);
			logger.info("doctor id : " + data1.getDoctor_id());

			/// NEED TO IMPLEMENT OUTBOX PATTERN HERE FOR HANDLING NOTIFICATION TO DOCTOR //

			this.notifyDoctorToApproveAppointment(p.getId(), p.getAppointment_scheduled_time(),
					data.getDisease_description(),
					"Hello , Dr." + docDetails.getDoctor_name() + " one new Appointment has been booked for you by "
							+ patient_details.getPatient_name(),
					patient_details.getPhone_number(), p.getDisease_description(), docDetails.getPhone_number());
			return p;

		}

		return null;

	}

	public void notifyDoctorToApproveAppointment(int appointment_id, LocalDateTime scheduled_time, String disease,
			String details,
			String patient_phonenumber, String disease_description, String doctor_phone_number)
			throws JsonProcessingException {
		try {
			AppointmentDetails detailsToDoctor = new AppointmentDetails();
			Appointment_outbox_events outboxEvent = new Appointment_outbox_events();
			detailsToDoctor.setAppointment_booked_date(LocalDate.now());
			detailsToDoctor.setAppointment_booked_time(LocalTime.now());
			detailsToDoctor.setAppointment_scheduled(scheduled_time);
			detailsToDoctor.setDetails(details);
			detailsToDoctor.setPatient_phonenumber(patient_phonenumber);
			detailsToDoctor.setDisease_category(disease);
			detailsToDoctor.setDoctor_phoneNumber(doctor_phone_number);
			detailsToDoctor.setDetails("NEW");
			String json = objectMapper.writeValueAsString(detailsToDoctor);
			outboxEvent.setPayload(json);
			logger.info("Payload for Kafka: " + json);
			outboxEvent.setEventType("check-appointment-by-doctor-approve-reject");
			outboxEvent.setAggregateId(String.valueOf(appointment_id));
			this.outboxRepo.save(outboxEvent);

		} catch (Exception e) {
			logger.error("Error while sending Kafka message: " + e.getMessage());
		}

	}
	public void notifypatientsForAppointmentStatus(int appointment_id, String status, String doctor_name, String patient_phone_number) throws JsonProcessingException {
		try {
			AppointmentDetails detailsToPatient = new AppointmentDetails();
			Appointment_outbox_events outboxEvent = new Appointment_outbox_events();
			detailsToPatient.setAppointment_booked_date(LocalDate.now());
			detailsToPatient.setAppointment_booked_time(LocalTime.now());
			detailsToPatient.setDetails("Hello , Your appointment has been " + status + " by Dr." + doctor_name);
			detailsToPatient.setPatient_phonenumber(patient_phone_number);
			String json = objectMapper.writeValueAsString(detailsToPatient);
			outboxEvent.setPayload(json);
			logger.info("Payload for Kafka: " + json);
			outboxEvent.setEventType("update-appointment-by-doctor");
			outboxEvent.setAggregateId(String.valueOf(appointment_id));
			this.outboxRepo.save(outboxEvent);

		} catch (Exception e) {
			logger.error("Error while sending Kafka message: " + e.getMessage());
		}

	}   

	public Appointment_book_by_Patient handleCircuitBreaker(Appointment_Dto data, String userid, int disease_id,
			Throwable t) {
		logger.error("Circuit breaker triggered for saveAppointment: " + t.getMessage());
		System.out.println("Circuit Breaker called");
		return new Appointment_book_by_Patient();

	}

	@Transactional
	public ResponseEntity<?> approveOrRejectAppointmentByDoctor(int appointment_id, AppointmentStatus_Dto dto)
			throws Exception {

		try {
			Optional<Appointment_book_by_Patient> optional = this.AppointmentRepo.getAppointmentDetailsById(
					appointment_id,
					dto.getDoctor_id());

			if (optional.isEmpty()) {
				return ResponseEntity.badRequest().body("No Appointment Found");
			}

			Appointment_book_by_Patient data = optional.get();
			int pendingStatusId = this.AppointmentStatusRepo.findIdByStatusName("pending");

			// Validate status
			String status = dto.getStatus().toUpperCase();
			if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
				System.out.println("Invalid status: " + status);
				return ResponseEntity.badRequest().body("Invalid Status");
			}

			// Prevent double update
			if (data.getAppointment_status_id() != pendingStatusId)
			// && (this.AppointmentStatusRepo.findIdByStatusName("pending"))
			{
				System.out.println("Appointment already processed");
				return ResponseEntity.badRequest().body("Already processed");
			}

			// Update status
			int statusId = this.AppointmentStatusRepo.findIdByStatusName(status.toLowerCase());
			data.setAppointment_status_id(statusId);
			data.setApprovedOrRejected_At(LocalDateTime.now());
			this.AppointmentRepo.save(data);
			DoctorDetailsToAppointment doctor_name = this.doctorFeign.sendDoctorDetailsToAppointment(dto.getDoctor_id());
			Patient_Details_To_Admin patient_details = this.feign.getPatientById(data.getPatient_id());
			this.notifypatientsForAppointmentStatus(appointment_id, status, doctor_name.getDoctor_name(), patient_details.getPhone_number());
			return ResponseEntity.ok("Appointment " + status + " successfully");

		} catch (ObjectOptimisticLockingFailureException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflict: Appointment was updated by another transaction. Please try again.");
		}
	}

	public List<AppointmentViewByDoctor> getAllAppointments(String status, int doctor_id) {
		try {
			List<AppointmentViewByDoctor> lists = this.AppointmentRepo.checkIfAnyAppointmentPending(status, doctor_id);
			if (!lists.isEmpty()) {
				return lists;
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}
