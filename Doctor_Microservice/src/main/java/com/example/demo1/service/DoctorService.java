package com.example.demo1.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo1.dto.DoctorBasicDetailsRequest;
import com.example.demo1.dto.DoctorBasicDetailsResponse;
import com.example.demo1.dto.DoctorDetailsDTO;
import com.example.demo1.dto.DoctorDetailsDTO_Redis;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.model.DoctorRegistrationDocuments;
import com.example.demo1.repo.DoctorRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorService {
	private final DoctorRepo repo;
	private final RedisTemplate<String, List<DoctorDetailsDTO_Redis>> redisTemplate;
	private final S3Service s3Service;

	public DoctorService(DoctorRepo repo, RedisTemplate<String, List<DoctorDetailsDTO_Redis>> redisTemplate,
			S3Service s3Service) {
		this.repo = repo;
		this.redisTemplate = redisTemplate;
		this.s3Service = s3Service;
	}

	public boolean isExistsDoctor(long id) {
		return this.repo.existsById(id);
	}

	public int getId(String name) {
		return this.repo.getIdFromLoginByUsername(name);
	}

	public Optional<DoctorBasicDetailsResponse> saveOrUpdateBasicDetails(DoctorBasicDetailsRequest d, long auth_user_id)
		// String fileName,
			//String fileType)
			 {
		try {
			Optional<Doctor> existingDoctor = this.repo.findByAuthUserId(auth_user_id);
			if (existingDoctor.isPresent()) {
				Doctor doctorToUpdate = existingDoctor.get();
				doctorToUpdate.setDoctor_name(d.getDoctor_name());
				doctorToUpdate.setPhone_number(d.getPhone_number());
				doctorToUpdate.setRegistrationNumber(d.getRegistrationNumber());
				doctorToUpdate.setCity(d.getCity());
				doctorToUpdate.setExperience(d.getExperience());
				doctorToUpdate.setPincode(d.getPincode());
				doctorToUpdate.setSpeclization(d.getSpeclization());
				String key = "doctors/%s/registration/%s_%s"
						.formatted(auth_user_id, UUID.randomUUID(), d.getFileName());

				DoctorRegistrationDocuments registrationDocs = new DoctorRegistrationDocuments();
				registrationDocs.setDoctor_id(auth_user_id);
				registrationDocs.setS3Key(key);
				registrationDocs.setFileName(d.getFileName());
				String uploadFileUrl = s3Service.generatePresignedUrlForUpload(key, d.getMimeType());
				this.s3Service.saveFileToDB(registrationDocs);
				if (redisTemplate.opsForValue().get(d.getCity() + ":" + d.getExperience()) != null) {
					log.info("Cache hit for key: " + d.getCity() + ":" + d.getExperience());
					redisTemplate.delete(d.getCity() + ":" + d.getExperience());
				}
				Doctor doctor = this.repo.save(doctorToUpdate);
				return Optional.of(new DoctorBasicDetailsResponse
					(
						doctor.getDoctor_name(),
						doctor.getPhone_number(),
						doctor.getRegistrationNumber(),
						doctor.getCity(),
						doctor.getSpeclization(),
						doctor.getExperience(),
						doctor.getPincode(),
						key,
						uploadFileUrl
					));
			}
			log.info("hit save service");
			Doctor doctor = new Doctor();
			if (d != null) {
				log.info("Inside Save logic: " + (d.getDoctor_name() != null ? d.getDoctor_name() : "null"));
				doctor.setAuth_user_id(auth_user_id);
				doctor.setDoctor_name(d.getDoctor_name());
				doctor.setPhone_number(d.getPhone_number());
				doctor.setRegistrationNumber(d.getRegistrationNumber());
				doctor.setRegistrationVerified(true);
				doctor.setCity(d.getCity());
				doctor.setExperience(d.getExperience());
				doctor.setPincode(d.getPincode());
				doctor.setSpeclization(d.getSpeclization());
				String key = "doctors/%s/registration/%s_%s"
						.formatted(auth_user_id, UUID.randomUUID(), d.getFileName());
				String uploadFileUrl = s3Service.generatePresignedUrlForUpload(key, d.getMimeType());
				if (redisTemplate.opsForValue().get(d.getCity() + ":" + d.getExperience()) != null) {
					log.info("Cache hit for key: " + d.getCity() + ":" + d.getExperience());
					redisTemplate.delete(d.getCity() + ":" + d.getExperience());
				}
				this.repo.save(doctor);
				return Optional.of(new DoctorBasicDetailsResponse
					(
						doctor.getDoctor_name(),
						doctor.getPhone_number(),
						doctor.getCity(),
						doctor.getRegistrationNumber(),
						doctor.getPincode(),
						doctor.getExperience(),
						doctor.getSpeclization(),
						key,
						uploadFileUrl
					));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public int getAllDoctorsCount() {
		try {
			int count = this.repo.countOfVerifiedDoctors();
			return count;
		} catch (Exception e) {
		}
		return 0;

	}

	public Optional<Doctor> getDoctorByUserId(long id) {
		return this.repo.findByAuthUserId(id);
	}

	public long FindIDByDoctorNameAndCityName(String name, String city) {
		long id = this.repo.findIdByDoctornameAndCityName(name, city);
		return id;

	}

	@SuppressWarnings({ "null" })
	public List<DoctorDetailsDTO_Redis> getAllDoctorsByCityAndExperience(String city, int experience) {
		String key = city + ":" + experience;
		log.info("Found  key log: " + key);
		if (redisTemplate.opsForValue().get(key) != null) {
			List<DoctorDetailsDTO_Redis> listFromRedis = redisTemplate.opsForValue().get(key);
			log.info("Length of list from redis for key " + key + ": " + listFromRedis.size());
			if (listFromRedis.isEmpty()) {
				log.info("Found empty list in redis for key: " + key);
				return null;
			}
			log.info("Found list in redis for key: " + key);
			return listFromRedis;
		}

		List<DoctorDetailsDTO> lists = this.repo.getApprovedDoctorsList(city, experience);
		List<DoctorDetailsDTO_Redis> redisList = lists.stream()
				.map(p -> new DoctorDetailsDTO_Redis(
						p.getDoctor_Name(),
						p.getPhone_Number(),
						p.getCity(),
						p.getExperience(),
						p.getSpeclization()
				// p.getS3Key(),
				))
				.collect(Collectors.toList());
		redisTemplate.opsForValue().set(key, redisList, Duration.ofDays(1));
		if (redisList.isEmpty()) {
			return null;
		}
		return redisList;

	}

}
