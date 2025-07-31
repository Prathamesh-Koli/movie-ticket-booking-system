package com.bookar.controller;

	import com.razorpay.*;
	import org.json.JSONObject;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.HashMap;
	import java.util.Map;

	@RestController
	@RequestMapping("/api/payment")
	public class PaymentController {

	    @Value("${razorpay.keyId}")
	    private String keyId;

	    @Value("${razorpay.keySecret}")
	    private String keySecret;

	    @PostMapping("/create-order")
	    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
	        int amount = (int) data.get("amount");

	        RazorpayClient client = new RazorpayClient(keyId, keySecret);

	        JSONObject options = new JSONObject();
	        options.put("amount", amount * 100); // convert to paise
	        options.put("currency", "INR");
	        options.put("receipt", "order_rcptid_11");

	        Order order = client.orders.create(options);		

	        Map<String, Object> response = new HashMap<>();
	        response.put("orderId", order.get("id"));
	        response.put("amount", order.get("amount"));
	        response.put("currency", order.get("currency"));
	        response.put("key", keyId);

	        return ResponseEntity.ok(response);
	    }
	}


