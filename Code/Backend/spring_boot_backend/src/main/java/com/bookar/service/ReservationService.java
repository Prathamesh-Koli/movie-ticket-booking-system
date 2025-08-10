package com.bookar.service;

import com.bookar.dto.ReservationRequestDTO;
import com.bookar.dto.ReservationResponseDTO;

public interface ReservationService {
	public ReservationResponseDTO reserveSeats(ReservationRequestDTO req);
	void expireOldReservations();
    ReservationResponseDTO getReservationById(Long id);
}
