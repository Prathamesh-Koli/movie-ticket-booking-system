package com.bookar.service;

import com.bookar.dto.ReservationRequestDTO;
import com.bookar.dto.ReservationResponseDTO;
import com.bookar.dto.SetReservationResp;

public interface ReservationService {
	public SetReservationResp reserveSeats(ReservationRequestDTO req);
	void expireOldReservations();
    ReservationResponseDTO getReservationById(Long id);
}
