package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.DoctorRegistrationDocuments;
@Repository
public interface DoctorRegistrationDocsRepo extends JpaRepository<DoctorRegistrationDocuments, Long> {
    
}
