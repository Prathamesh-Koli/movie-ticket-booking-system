package com.bookar.service;

import java.util.List;
import com.bookar.dto.MovieDetailDTO;

import com.bookar.dto.MovieResponseDTO;

public interface MovieService {
	MovieResponseDTO getMovieById(Long id);
	
	List<String> getLocationsForMovie(Long movieid);
	MovieDetailDTO getMovieDetailsById(Long id);


}
