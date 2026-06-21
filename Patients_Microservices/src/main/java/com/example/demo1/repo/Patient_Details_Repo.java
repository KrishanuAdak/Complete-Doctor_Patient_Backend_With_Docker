package com.example.demo1.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Patient_Details_To_Admin;
import com.example.demo1.model.Patients;

@Repository
public interface Patient_Details_Repo extends JpaRepository<Patients,Integer>{
	
	@Query(value="select patient_name,patient_phone_number from patient_details where patient_id=?1",nativeQuery=true)
	public Patient_Details_To_Admin getPatientById(int id);

}
