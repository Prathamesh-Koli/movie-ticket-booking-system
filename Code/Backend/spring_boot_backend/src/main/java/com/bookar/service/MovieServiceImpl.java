package com.bookar.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.custom_exceptions.ResourceNotFoundException;
import com.bookar.dao.MovieDao;
import com.bookar.dao.ShowDao;
import com.bookar.dto.MovieResponseDTO;
import com.bookar.entities.Movie;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
	private final MovieDao movieDao;
	private final ShowDao showDao;
	private final ModelMapper modelMapper;

	@Override
	public MovieResponseDTO getMovieById(Long id) {
		Movie movie = movieDao.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("Movie not found with id: " + id));
		
		return modelMapper.map(movie, MovieResponseDTO.class);
	}

	@Override
	public List<String> getLocationsForMovie(Long movieId) {
		return showDao.findDistinctLocationsByMovieId(movieId);
	}
	
	
}
