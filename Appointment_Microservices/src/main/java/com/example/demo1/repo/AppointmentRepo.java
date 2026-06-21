package com.example.demo1.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.dto.AppointmentViewByDoctor;
import com.example.demo1.model.Appointment_book_by_Patient;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment_book_by_Patient, Integer> {

	@Query(value = """
			select
			a.disease_category as DiseaseType,
			a.appointment_scheduled_time as AppointmentScheduled,
			s.status_name as AppointmentStatus,
			p.patient_name as PatientName,
			from appointment_booked_by_patients a
			inner join patient_details p
			on p.patient_id=a.patient_id
			inner join appointment_status s
			on a.appointment_status_id=s.id where s.status_name=?1 and a.doctor_id=?2;
			""", nativeQuery = true)
	List<AppointmentViewByDoctor> checkIfAnyAppointmentPending(String status, int doctor_id);

	@Query(value = "select * from appointment_booked_by_patients where id=?1 and doctor_id=?2", nativeQuery = true)
	public Optional<Appointment_book_by_Patient> getAppointmentDetailsById(long appointment_id,long doctor_id);
    
	@Query(value = "select count(a.id) from appointment_booked_by_patients a join appointment_status s on a.appointment_status_id=s.id where s.status_name='approved';", nativeQuery = true)
	public int countOfCompletedAppointments();

	
}
