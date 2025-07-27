package com.bookar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.bookar.dto.MovieResponseDTO;
import com.bookar.service.MovieService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/movies")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class MovieController {
	
	private final MovieService movieService;
	
	@GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long id) {
        MovieResponseDTO dto = movieService.getMovieById(id);
        return ResponseEntity.ok(dto);
    }
	
	@GetMapping("/{movieId}/locations")
    public ResponseEntity<List<String>> getLocationsForMovie(@PathVariable Long movieId) {
        List<String> locations = movieService.getLocationsForMovie(movieId);
        return ResponseEntity.ok(locations);
    }

}
