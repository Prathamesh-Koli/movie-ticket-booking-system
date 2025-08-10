package com.bookar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookar.dto.TheaterRequestDTO;
import com.bookar.dto.TheaterResponseDTO;
import com.bookar.entities.Theater;
import com.bookar.entities.TheaterStatus;
import com.bookar.service.TheaterService;
import lombok.RequiredArgsConstructor;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    // Theater Owner - Add Theater
    @PostMapping("/add")
    public ResponseEntity<?> addTheater(@RequestBody TheaterRequestDTO dto) {
        Theater theater = theaterService.addTheater(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Theater added successfully, awaiting admin approval",
                "theaterId", theater.getTheaterId()
        ));
    }

    // Admin - Get Pending Theaters
    @GetMapping("/pending")
    public ResponseEntity<List<Theater>> getPendingTheaters() {
        return ResponseEntity.ok(theaterService.getTheatersByStatus(TheaterStatus.PENDING));
    }

    // Admin - Approve/Reject Theater
    @PutMapping("/{theaterId}/status/{status}")
    public ResponseEntity<Theater> updateTheaterStatus(
            @PathVariable Long theaterId,
            @PathVariable TheaterStatus status) {
        return ResponseEntity.ok(theaterService.updateTheaterStatus(theaterId, status));
    }
    
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Map<String, List<Theater>>> getTheatersByOwner(@PathVariable Long ownerId) {
        List<Theater> approved = theaterService.getTheatersByOwnerAndStatus(ownerId, TheaterStatus.APPROVED);
        List<Theater> pending = theaterService.getTheatersByOwnerAndStatus(ownerId, TheaterStatus.PENDING);

        return ResponseEntity.ok(Map.of(
            "approved", approved,
            "pending", pending
        ));
    }
    
    @GetMapping("/admin/all")
    public ResponseEntity<List<Theater>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @DeleteMapping("/admin/{theaterId}")
    public ResponseEntity<?> deleteTheater(@PathVariable Long theaterId) {
        theaterService.deleteTheater(theaterId);
        return ResponseEntity.ok(Map.of("message", "Theater removed successfully"));
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<TheaterResponseDTO> getTheaterDetails(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterDetails(id));
    }



}

