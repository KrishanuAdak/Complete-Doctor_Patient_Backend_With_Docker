package com.example.demo1.service;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Appointment_Status_Mapping;
import com.example.demo1.model.PatientDetailsToNotify;
import com.example.demo1.model.Payments;
import com.example.demo1.openfeign.Patient_Feign;
import com.example.demo1.repo.PaymentsRepo;
import com.example.demo1.repo.PaymentsStatusRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentsService {
    @Value("${razorpay.key_id}")
    private String razorpay_key_id;
    @Value("${razorpay.key_secret}")
    private String razorpay_key_secret;
     @Autowired
    private Patient_Feign patientFeign;

    @Autowired
    private PaymentsRepo repo;

    @Autowired
    private PaymentsStatusRepo paymentsStatusRepo;
    
    // @Autowired(required=true)
    // private KafkaTemplate<String, PatientDetailsToNotify> kafkaTemplate;

   @KafkaListener(topics="update-appointment-by-doctor", groupId="payments-group")
public void consumeAppointmentStatusUpdate(Appointment_Status_Mapping status_mapping) {

    try {
        System.out.println("Received appointment: " + status_mapping.getAppointment_id());

        processPayment(
            status_mapping.getAppointment_id(),
            status_mapping.getPatient_id(),
            status_mapping.getDoctor_id()
        );

    } catch (Exception e) {
        System.out.println("Error processing payment: " + e.getMessage());
        // optionally send to retry topic / DLQ
    }
}
    public Object processPayment(int appointmentId, int patient_id, int doctor_id) throws RazorpayException {

    System.out.println("Processing payment for appointment ID: " + appointmentId);

    // 🔑 IDEMPOTENCY KEY
    String idempotencyKey = "APPT_" + appointmentId;

    // 🔁 CHECK IF ALREADY EXISTS
    Payments existing = repo.findByIdempotencyKey(idempotencyKey);

    if (existing != null) {
        System.out.println("Payment already exists for appointment: " + appointmentId);
        return existing;
    }

    RazorpayClient razorpayClient = new RazorpayClient(razorpay_key_id, razorpay_key_secret);

    JSONObject orderRequest = new JSONObject();
    orderRequest.put("amount", 100000);
    orderRequest.put("currency", "INR");
    orderRequest.put("receipt", "receipt#" + appointmentId + "_" + patient_id);
    orderRequest.put("payment_capture", true);

    Order order;

    try {
        order = razorpayClient.orders.create(orderRequest);
    } catch (Exception e) {
        throw new RuntimeException("Razorpay order creation failed", e);
    }

    BigDecimal amount = BigDecimal.valueOf((Integer) order.get("amount"));

    Payments payment = new Payments();

    payment.setAmount(amount);
    payment.setAppointmentId(appointmentId);
    payment.setCurrency(order.get("currency").toString());
    payment.setUserId(patient_id);
    payment.setPayment_status(
        paymentsStatusRepo.findStatusIdByStatus("pending")
    );

    payment.setRazorpayOrderId(order.get("id").toString());

    payment.setIdempotencyKey(idempotencyKey);

    // optional
    payment.setPaymentLink("https://checkout.razorpay.com/v1/checkout.js?order_id=" + order.get("id"));

    repo.save(payment);

    System.out.println("Order created: " + order.get("id"));

    // 🔔 Send notification
    sendPaymentNotificationToPatient(patient_id);

    return order;
}
   
    public void sendPaymentNotificationToPatient(int patient_id) {
        // Send payment notification to patient via Kafka

         PatientDetailsToNotify Details=this.patientFeign.getPatientNameById(patient_id);
         
        // kafkaTemplate.send("payment-pending-notification-to-patient", patientDetails);
    }
}