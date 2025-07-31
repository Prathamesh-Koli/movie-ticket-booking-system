package com.bookar.service;

import com.razorpay.RazorpayException;

import java.util.Map;
import com.bookar.dto.*;
public interface PaymentService {
    PaymentResponseDTO createOrder(PaymentRequestDTO dto) throws RazorpayException;
}
