package com.bookar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookar.dto.ApiResponseDTO;
import com.bookar.dto.LayoutRequestDTO;
import com.bookar.dto.SeatLayoutResponseDTO;
import com.bookar.dto.TheaterInfoDTO;
import com.bookar.service.TheaterService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/theaters")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping("/{id}")
    public ResponseEntity<TheaterInfoDTO> getTheaterById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }
    
    @PostMapping("/{theaterId}/savelayout")
    public ResponseEntity<ApiResponseDTO> addLayout(
        @PathVariable Long theaterId,
        @RequestBody LayoutRequestDTO request
    ) {
      theaterService.saveLayoutForAllScreens(theaterId, request.getLayout());
      return ResponseEntity.ok(new ApiResponseDTO(true, "Layout saved successfully!"));
    }
    
    
    @GetMapping("/{theaterId}/getlayout")
    public ResponseEntity<List<SeatLayoutResponseDTO>> getSavedLayout(@PathVariable Long theaterId) {
        List<SeatLayoutResponseDTO> layout = theaterService.getSavedLayout(theaterId);
        return ResponseEntity.ok(layout);
    }
    
    
}