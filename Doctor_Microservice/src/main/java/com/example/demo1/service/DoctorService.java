package com.example.demo1.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.repo.DoctorRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorService {
	private final DoctorRepo repo;


	public DoctorService(DoctorRepo repo) {
		this.repo = repo;
	}


	public boolean isExistsDoctor(long id) {
		return this.repo.existsById(id);
	}

	public int getId(String name) {
		return this.repo.getIdFromLoginByUsername(name);
	}

	public Optional<Doctor> saveBasicDetails(Doctor d,long auth_user_id) {
		log.info("hit save service");
		Doctor doctor = new Doctor();
		if (d != null) {

			doctor.setAuth_user_id(auth_user_id);
			doctor.setDoctor_name(d.getDoctor_name());
			doctor.setPhone_number(d.getPhone_number());
			doctor.setFileName(d.getFileName());
			doctor.setRegistrationFile_Url(d.getRegistrationFile_Url());
			doctor.setRegistrationNumber(d.getRegistrationNumber());
			doctor.setRegistrationVerified(false);
			doctor.setCity(d.getCity());
			doctor.setExperience(d.getExperience());
			doctor.setPincode(d.getPincode());
			doctor.setSpeclization(d.getSpeclization());

			return Optional.of(this.repo.save(doctor));

		}
		return Optional.empty();

	}

	public DoctorDetailsToAppointment findNameAndPhoneById(int id) {
		DoctorDetailsToAppointment data = this.repo.findDetailsById(id);
		if (data != null) {
			return data;
		}
		return data;
	}

	public int getAllDoctors() {
		try {
			int count = this.repo.countOfVerifiedDoctors();
			return count;
		} catch (Exception e) {
		}
		return 0;

	}

	@Cacheable(value = "doctors", key = "#id")
	public Doctor getById(long id) {
		return this.repo.findById(id).orElseThrow(() -> new RuntimeException("Doctor id not found"));
	}

	// Redis
	@CachePut(value = "doctors", key = "#id")
	public Doctor updateById(Doctor d, int id) {
		Doctor s = d;
		// s.setEmail(d.getEmail());
		// s.setPassword(d.getPassword());
		s.setCreation_date(d.getCreation_date());
		Doctor x = this.repo.save(s);
		System.out.println(x);
		return x;
	}

	@CacheEvict(value = "doctors", key = "#id")
	public void delete(long id) {
		this.repo.deleteById(id);

	}

	public void updateApprovalStatus(long id, String status) {
		Doctor d = this.repo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));

		this.repo.updateApprovalStatusByAdmin(status, id);

	}

	public long  FindIDByDoctorNameAndCityName(String name,String city) {
		long id=
		this.repo.findIdByDoctornameAndCityName(name,city);
		// if(doctorDetails.isEmpty()){
		// 	return null;
		// }
		return id;
	
	}

}
