package com.bookar.service;

import com.bookar.dto.ReservationRequestDTO;
import com.bookar.dto.ReservationResponseDTO;

public interface ReservationService {

    // Creates a reservation and returns detailed booking info
    ReservationResponseDTO reserveSeats(ReservationRequestDTO req);

    // Deletes or marks old reservations as expired
    void expireOldReservations();

    // Fetches reservation by its ID with full details
    ReservationResponseDTO getReservationById(Long id);
}
