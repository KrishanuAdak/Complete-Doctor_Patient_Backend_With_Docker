package com.example.demo1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.LoginRecordsDoctor;

@Repository
public interface Doctor_Login_Records extends JpaRepository<LoginRecordsDoctor,Integer>{
	@Query(value="select * from doctor_login_records where doctor_id=?1 ",nativeQuery=true)
	public LoginRecordsDoctor getDoctorId(int id);

}
