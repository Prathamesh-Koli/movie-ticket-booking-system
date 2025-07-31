package com.bookar.service;

import com.razorpay.RazorpayException;

import java.util.Map;

public interface PaymentService {
    Map<String, Object> createOrder(int amount) throws RazorpayException;
}
