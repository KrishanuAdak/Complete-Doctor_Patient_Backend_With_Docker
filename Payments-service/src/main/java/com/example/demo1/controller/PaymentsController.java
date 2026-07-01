package com.example.demo1.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.model.PaymentEvent;
import com.example.demo1.model.Payments;
import com.example.demo1.repo.PaymentEventRepo;
import com.example.demo1.repo.PaymentsRepo;
import com.example.demo1.repo.PaymentsStatusRepo;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private final Logger logger = LoggerFactory.getLogger(PaymentsController.class);

    private final PaymentsRepo repo;

    private final PaymentsStatusRepo paymentsStatusRepo;

    private final PaymentEventRepo eventRepo;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    public PaymentsController(PaymentsRepo repo, PaymentEventRepo eventRepo,
            com.example.demo1.repo.PaymentsStatusRepo paymentsStatusRepo) {
        this.repo = repo;
        this.eventRepo = eventRepo;
        this.paymentsStatusRepo = paymentsStatusRepo;
    }

    // =========================================================
    // 🔐 OPTIONAL: FRONTEND VERIFY (NOT FINAL SOURCE OF TRUTH)
    // =========================================================
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        boolean isValid = verifyCheckoutSignature(orderId, paymentId, signature);

        Payments payment = repo.findByRazorpayOrderId(orderId);

        if (payment == null) {
            return ResponseEntity.badRequest().body("Payment not found");
        }

        if (isValid) {
            payment.setRazorpayPaymentId(paymentId);
            payment.setRazorpaySignature(signature);

            // ⚠️ DO NOT mark success here (only webhook should)
            repo.save(payment);

            return ResponseEntity.ok("Payment verified (awaiting webhook)");
        } else {
            return ResponseEntity.badRequest().body("Invalid signature");
        }
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Payments Service!";
    }

    // =========================================================
    // 🔥 MAIN: WEBHOOK (FINAL SOURCE OF TRUTH)
    // =========================================================
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestHeader("X-Razorpay-Signature") String signature,
            @RequestBody String payload) {
        logger.info("Received webhook: " + payload);

        processWebhook(payload, signature);
        return ResponseEntity.ok("Webhook processed");
    }

    // =========================================================
    // 🔐 WEBHOOK PROCESSING LOGIC
    // =========================================================
    @Transactional
    private void processWebhook(String payload, String signature) {

        if (!verifyWebhookSignature(payload, signature)) {
            throw new RuntimeException("Invalid webhook signature");
        }

        JSONObject json = new JSONObject(payload);

        String eventId = json.getString("id");

        if (eventRepo.existsByEventId(eventId)) {
            return;
        }

        String eventType = json.getString("event");

        JSONObject entity = json.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String orderId = entity.getString("order_id");
        String paymentId = entity.getString("id");
        String status = entity.optString("status");

        System.out.println("Webhook: " + eventId + " Event: " + eventType);

        Payments payment = repo.findByRazorpayOrderId(orderId);

        if (payment == null) {
            throw new RuntimeException("Payment not found for order: " + orderId);
        }

        if ("payment.captured".equals(eventType) || "captured".equals(status)) {
            payment.setPayment_status(
                    paymentsStatusRepo.findStatusIdByStatus("success"));
        } else if ("payment.failed".equals(eventType) || "failed".equals(status)) {
            payment.setPayment_status(
                    paymentsStatusRepo.findStatusIdByStatus("failed"));
        }

        payment.setRazorpayPaymentId(paymentId);

        repo.save(payment);

        // ✅ Save event AFTER success
        PaymentEvent event = new PaymentEvent();
        event.setEventId(eventId);
        event.setPayload(payload);

        eventRepo.save(event);
    }

    // =========================================================
    // 🔐 VERIFY CHECKOUT SIGNATURE (frontend response)
    // =========================================================
    private boolean verifyCheckoutSignature(String orderId, String paymentId, String signature) {
        try {
            String data = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(
                    razorpayKeySecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");

            mac.init(key);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            String generated = Hex.encodeHexString(rawHmac);

            return MessageDigest.isEqual(
                    generated.getBytes(),
                    signature.getBytes());

        } catch (Exception e) {
            throw new RuntimeException("Checkout signature verification failed");
        }
    }

    // =========================================================
    // 🔐 VERIFY WEBHOOK SIGNATURE (payload-based)
    // =========================================================
    private boolean verifyWebhookSignature(String payload, String signature) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec key = new SecretKeySpec(
                    razorpayKeySecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");

            mac.init(key);

            byte[] raw = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            String generated = Hex.encodeHexString(raw);

            return MessageDigest.isEqual(
                    generated.getBytes(),
                    signature.getBytes());

        } catch (Exception e) {
            throw new RuntimeException("Webhook signature verification failed");
        }
    }
}