package com.bookar.controller;

import com.bookar.dto.ReservationRequestDTO;
import com.bookar.dto.ReservationResponseDTO;
import com.bookar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@PostMapping
	public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO requestDTO) {
	    ReservationResponseDTO responseDTO = reservationService.reserveSeats(requestDTO);
	    return ResponseEntity.ok(responseDTO);
	}

	@DeleteMapping("/expire-old")
	public ResponseEntity<Void> expireOldReservations() {
	    reservationService.expireOldReservations();
	    return ResponseEntity.noContent().build();
	}

}