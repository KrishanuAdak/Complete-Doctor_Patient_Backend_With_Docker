package com.example.demo1.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Patients;
@Repository
public interface PatientsRepo extends JpaRepository<Patients,Integer> {
	
	@Query(value="select * from patients_list_01 where lock_version=false",nativeQuery=true)
	public List<Patients> getAll();
	
	@Query(value="select * from patients_list_01 where email=?1",nativeQuery=true)
	public Optional<Patients> getByPatientEmail(String email);
	
	@Query(value="select * from patients_list_01 where username LIKE (lower(concat('%',?1,'%')))",nativeQuery=true)
	public List<Patients> showPatientsByName(@Param("username") String username);

}
