package com.example.demo1.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo1.model.Patient_Details_To_Admin;
import com.example.demo1.model.Patients;
import com.example.demo1.repo.PatientsRepo;
@Service
public class PatientsService {

	private final PatientsRepo repo;

	public PatientsService(PatientsRepo repo) {
		this.repo = repo;
	}

	public Patients saveOrUpdatePatientDetails(Patients p,long patient_id) {
		Optional<Patients> getDetails=this.repo.findDetailsByAuthUserId(patient_id);
		if(getDetails.isEmpty()){
		if (p != null) {
			Patients detailsPatient = new Patients();
			detailsPatient.setCity(p.getCity());
			detailsPatient.setPhone_number(p.getPhone_number());
			detailsPatient.setPin(p.getPin());
			detailsPatient.setPatient_id(patient_id);
			Patients savedPatient = this.repo.save(detailsPatient);
			return savedPatient;
		}
	}else{
		Patients detailsPatient=p;
		     detailsPatient.setCity(p.getCity());
			detailsPatient.setPhone_number(p.getPhone_number());
			detailsPatient.setPin(p.getPin());
			detailsPatient.setPatient_id(patient_id);
			Patients savedPatient = this.repo.save(detailsPatient);
            return savedPatient;
	}
	return null;

	}

	public Optional<Patients> getPatientByName(String name) {
		return this.repo.showPatientByName(name);

	}
	public Patient_Details_To_Admin getPatientById(long id) throws Exception{
		Optional<Patients> isExists=this.repo.findDetailsByAuthUserId(id);
		if(isExists.isEmpty()){
			throw new Exception("Patient Details Not Found");
		}
		Patient_Details_To_Admin pp=new Patient_Details_To_Admin();
		pp.setPatient_name(this.repo.findDetailsByAuthUserId(id).get().getFullName());
		pp.setPhone_number(this.repo.findDetailsByAuthUserId(id).get().getPhone_number());
		return pp;

	}


}
