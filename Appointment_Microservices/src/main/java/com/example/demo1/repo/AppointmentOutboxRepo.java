package com.example.demo1.repo;
import com.example.demo1.model.Appointment_outbox_events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentOutboxRepo extends JpaRepository<Appointment_outbox_events, Integer> {
    @Query("SELECT e FROM Appointment_outbox_events e WHERE e.status = :status")
    public List<Appointment_outbox_events> findAllByStatus(String status);

}
