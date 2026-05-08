package com.example.demo1.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
	 
    @Query(value="select doctor_name as doctor_Name,phone_number as phone_number from doctor_basic_details where doctor_id=?1",nativeQuery=true)
	public DoctorDetailsToAppointment findDetailsById(int id);
	
	
	@Query(value="select id from doctor_list where doctor_name=?1",nativeQuery=true)
	public int getIdFromLoginByUsername(String doctor_name);
		
	
	@Query(value="select email from doctor where email=?1",nativeQuery=true)
	public String findByEmail(String email);

	
	@Query(value="select * from doctor_list where doctor_name=?1",nativeQuery=true)
    public Optional<Doctor> findByDoctorname(String doctor_name);
	
	
	@Query(value="select approval_status from doctor_list where doctor_name=?1",nativeQuery=true)
	public String checkApprovalStatus(String doctor_name);
	
	@Modifying
	@Transactional
	@Query(value="update doctor set approval_status=?1 where id=?2 and lock_version=false",nativeQuery=true)
	public void updateApprovalStatusByAdmin(String approval_status,int id);
	

	@Query(value="select * from doctor_list where (?1 is null  or doctor_name like (lower(concat('%',?1,'%')))) or (?2 is null or email like lower(concat('%',?2,'%')))",nativeQuery=true)
	public List<Doctor> searchByDoctor(@Param("name") String name, @Param("email") String email);
	
	
	@Query(value="select * from doctor_list where lock_version=false",nativeQuery=true)
    public List<Doctor> getAllDoctorsByFile();
    
	@Query(value="select count(*) from doctor_basic_details where is_Registration_Verified = true ",nativeQuery=true)
	public int countOfVerifiedDoctors();
	

}
