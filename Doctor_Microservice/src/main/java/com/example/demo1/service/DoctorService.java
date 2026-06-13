package com.example.demo1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.repo.DoctorRepo;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepo repo;


	public boolean isExistsDoctor(int id) {
		return this.repo.existsById(id);
	}

	public int getId(String name) {
		return this.repo.getIdFromLoginByUsername(name);
	}

	public Doctor saveBasicDetails(Doctor d) {
		Doctor doctor = new Doctor();
		if (d != null) {

			doctor.setAuth_user_id(d.getAuth_user_id());
			doctor.setCreation_date(LocalDateTime.now());
			doctor.setDoctor_name(d.getDoctor_name());
			doctor.setPhone_number(doctor.getPhone_number());
			doctor.setFileName(d.getFileName());
			doctor.setRegistrationFile_Url(d.getRegistrationFile_Url());
			doctor.setRegistrationNumber(d.getRegistrationNumber());
			doctor.setRegistrationVerified(false);
			doctor.setCity(d.getCity());
			doctor.setExperience(d.getExperience());
			doctor.setPincode(d.getPincode());
			Doctor dd = this.repo.save(doctor);
			return dd;

		}
		return null;

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
	public Doctor getById(int id) {
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
	public void delete(int id) {
		this.repo.deleteById(id);

	}

	public void updateApprovalStatus(int id, String status) {
		Doctor d = this.repo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));

		this.repo.updateApprovalStatusByAdmin(status, id);

	}

	// public List<Doctor> getBySearch(String name, String email) {
	// 	List<Doctor> doctor = this.repo.searchByDoctor(name, email);
	// 	if (doctor == null) {
	// 		return null;
	// 	}
	// 	return doctor;
	// }

}
