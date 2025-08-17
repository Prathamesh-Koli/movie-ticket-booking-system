package com.bookar.controller;


import com.bookar.dto.ReservationResponseDTO;
import com.bookar.dto.SeatResponseDTO;
import com.bookar.dto.SeatReservationResp;
import com.bookar.service.ReservationService;
import com.bookar.service.SeatService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seatselection")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class SeatSelectionController {
    
    private final SeatService seatService;
    

   
    @GetMapping("/show/{showId}")
    public ResponseEntity<List<SeatResponseDTO>> getSeats(@PathVariable Long showId,@RequestParam Long theaterId) {
        List<SeatResponseDTO> seats = seatService.getSeatsForShow(showId, theaterId);
        return ResponseEntity.ok(seats);
    }

    
}
   