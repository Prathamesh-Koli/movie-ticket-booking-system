package com.bookar.service;

import java.util.List;

import com.bookar.dto.MovieResponseDTO;

public interface MovieService {
	MovieResponseDTO getMovieById(Long id);
	
	List<String> getLocationsForMovie(Long movieid);
}
