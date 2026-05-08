package com.example.demo1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Available_Doctor_Now;
import com.example.demo1.model.Available_Doctor_Respponse;

import jakarta.transaction.Transactional;
@Repository
public interface Available_Doctor_Now_Repo  extends JpaRepository<Available_Doctor_Now,Integer>{
	
	
	@Query(value="select d.id from doctor d inner join available_doctors_now_01 a on d.id=a.doctor_id where a.end_time >= now() +interval 1 minute",nativeQuery=true)
	public List<Integer> getAllAvailableDoctors();
	
	@Query(value="select doctor_id from available_doctors_now_01 where doctor_id=?1 and end_time>=now() + interval 40 minute",nativeQuery=true)
	public int forwardAvailableDoctorId(int id);
	 
	@Query(value="delete from available_doctors_now_01 where end_time < now()",nativeQuery=true)
	public void deleteEntries();
	
	
	@Modifying
	@Transactional
	@Query(value="delete from available_doctors_now_01 where end_time < now()", nativeQuery=true) 
	public void deleteRecords();

}
