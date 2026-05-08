package com.example.demo1.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.AuthDB;

@Repository
public interface AuthRepo extends JpaRepository<AuthDB,Integer>{
	
	@Query(value="select * from authdb where email=?1",nativeQuery=true)
	public Optional<AuthDB> findByEmail(String email);

	@Query(value="select email from authdb where role=patient and id=?1",nativeQuery=true)
	public String getEmailByPatientId(int id);

}
