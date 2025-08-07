package com.bookar.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.bookar.dto.*;
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    @Override
    public PaymentResponseDTO createOrder(PaymentRequestDTO dto) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", dto.getAmount() * 100);
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        return new PaymentResponseDTO(
            order.get("id"),
            order.get("amount"),
            order.get("currency"),
            keyId
        );
    }

}
