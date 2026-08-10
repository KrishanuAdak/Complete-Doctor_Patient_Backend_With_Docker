package com.example.demo1.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.dto.DoctorDetailsDTO;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;

import jakarta.transaction.Transactional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
	 
    @Query(value="select doctor_name as doctor_Name,phone_number as phone_number from doctor_basic_details where auth_user_id=?1",nativeQuery=true)
	public DoctorDetailsToAppointment findDetailsById(long  id);
	
	
	@Query(value="select id from doctor_basic_details where doctor_name=?1",nativeQuery=true)
	public int getIdFromLoginByUsername(String doctor_name);
		
	
	@Query(value="select email from doctor_basic_details where email=?1",nativeQuery=true)
	public String findByEmail(String email);

	
	@Query(value="select auth_user_id from doctor_basic_details where doctor_name=?1 and  city= ?2",nativeQuery=true)
    public long findIdByDoctornameAndCityName(String doctor_name , String city);
	
	
	
	@Modifying
	@Transactional
	@Query(value="update doctor set approval_status=?1 where id=?2 and lock_version=false",nativeQuery=true)
	public void updateApprovalStatusByAdmin(String approval_status,long id);
	

	@Query(value="select count(*) from doctor_basic_details where is_Registration_Verified = true ",nativeQuery=true)
	public int countOfVerifiedDoctors();
 
    @Query(value="select * from Doctor_Basic_Details where auth_user_id=?1",nativeQuery=true)
	public Optional<Doctor> findByAuthUserId(Long authUserId);

    @Query(value="select doctor_name,phone_number,city,experience,speclization from doctor_basic_details where is_Registration_Verified=true and (city=?1 or ?1 is null) and (?2 is null or experience<=?2)",nativeQuery=true)
	public List<DoctorDetailsDTO> getApprovedDoctorsList(String city,int experience);
	

}
