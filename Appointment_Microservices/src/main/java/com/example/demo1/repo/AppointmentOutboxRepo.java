package com.example.demo1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Appointment_outbox_events;

@Repository
public interface AppointmentOutboxRepo extends JpaRepository<Appointment_outbox_events, Integer> {
    @Query(value="SELECT * FROM Appointment_outbox_events  WHERE status = ?1 order by created_at asc limit 50",nativeQuery=true)
    public List<Appointment_outbox_events> findAllByStatus(String status);

}
