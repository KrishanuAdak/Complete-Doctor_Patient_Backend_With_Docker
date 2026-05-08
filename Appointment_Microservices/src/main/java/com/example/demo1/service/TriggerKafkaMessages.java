package com.example.demo1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo1.NotificationResponse.AppointmentDetails;
import com.example.demo1.model.Appointment_outbox_events;
import com.example.demo1.repo.AppointmentOutboxRepo;
import com.example.demo1.repo.AppointmentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TriggerKafkaMessages {

    @Autowired
    private AppointmentOutboxRepo outboxRepo;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppointmentRepo appointmentRepo;
    
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void triggerKafkaMessage() {

        List<Appointment_outbox_events> events = this.outboxRepo.findAllByStatus("new");

        for (int i=0; i < events.size(); i++) {
            Appointment_outbox_events event = events.get(i);

            try {
                AppointmentDetails details = objectMapper.readValue(event.getPayload(), AppointmentDetails.class);
                System.out.println("Appointment Scheduled: " + details.getAppointment_scheduled());
                this.kafkaTemplate.send(events.get(i).getEventType(), details);
                event.setStatus("SENT");

            } catch (Exception e) {
                e.printStackTrace();
                event.setStatus("FAILED");
            }

            outboxRepo.save(event);
        }
    }

    public int getCompletedAppointmentsCount() {
        try{
            return appointmentRepo.countOfCompletedAppointments();
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicating an error occurred
        }
    }
}
