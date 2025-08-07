package com.bookar.controller;
import java.util.Base64;
import java.time.LocalDateTime;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.bookar.dto.PaymentVerificationRequest;
import com.bookar.service.BookingService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentVerificationController {@Value("${razorpay.keyId}")
private String razorpayKeyId;

@Value("${razorpay.keySecret}")
private String razorpayKeySecret;

@Autowired
private BookingService bookingService;

//@PostMapping("/verify")
//public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest req) {
//    try {
//        String payload = req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId();
//        String expectedSignature = generateHmacSHA256(payload, razorpayKeySecret);
//
//        if (!expectedSignature.equals(req.getRazorpaySignature())) {
//            bookingService.cancelReservation(req.getReservationId());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature");
//        }
//
//        bookingService.confirmBooking(req.getReservationId(), req.getUserId(), req.getRazorpayPaymentId());
//        return ResponseEntity.ok("Booking Confirmed");
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Verification failed: " + e.getMessage());
//    }
//}

//private String generateHmacSHA256(String data, String key) {
//    try {
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
//        mac.init(secretKey);
//        byte[] hash = mac.doFinal(data.getBytes());
//        return Base64.getEncoder().encodeToString(hash);
//    } catch (Exception e) {
//        throw new RuntimeException("Error generating HMAC", e);
//    }
//}
@PostMapping("/verify")
public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest req) {
    try {
        String payload = req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId();
        String expectedSignature = generateHmacSHA256(payload, razorpayKeySecret); // fixed

        if (!expectedSignature.equals(req.getRazorpaySignature())) {
            bookingService.cancelReservation(req.getReservationId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature");
        }

        bookingService.confirmBooking(req.getReservationId(), req.getUserId(), req.getRazorpayPaymentId());
        return ResponseEntity.ok("Booking Confirmed");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Verification failed: " + e.getMessage());
    }
}

private String generateHmacSHA256(String data, String key) {
    try {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes());

        // FIX: Return hex-encoded string, not Base64
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    } catch (Exception e) {
        throw new RuntimeException("Error generating HMAC", e);
    }
}

}