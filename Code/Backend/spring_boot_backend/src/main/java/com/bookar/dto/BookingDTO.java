package com.bookar.dto;

import java.time.LocalDateTime;

public class BookingDTO {
private Long id;
private String paymentId;
private LocalDateTime bookingTime;
private Long reservationId;
private Long userId;
public BookingDTO(Long id, String paymentId, LocalDateTime bookingTime, Long reservationId, Long userId) {
    this.id = id;
    this.paymentId = paymentId;
    this.bookingTime = bookingTime;
    this.reservationId = reservationId;
    this.userId = userId;
}

public Long getId() {
    return id;
}

public String getPaymentId() {
    return paymentId;
}

public LocalDateTime getBookingTime() {
    return bookingTime;
}

public Long getReservationId() {
    return reservationId;
}

public Long getUserId() {
    return userId;
}
}