package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Payments_Status;
@Repository
public interface PaymentsStatusRepo extends JpaRepository<Payments_Status, Integer>{
    @Query("SELECT id FROM Payments_Status  WHERE lower(status_name) = lower(:status)")
    public int findStatusIdByStatus(String status);

}
