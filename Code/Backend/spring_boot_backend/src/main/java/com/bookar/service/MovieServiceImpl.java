package com.bookar.service;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bookar.custom_exceptions.ResourceNotFoundException;
import com.bookar.dao.MovieDao;
import com.bookar.dao.ShowDao;
import com.bookar.dto.MovieDetailDTO;
import com.bookar.dto.MovieResponseDTO;
import com.bookar.dto.MovieCastDTO;
import com.bookar.entities.Movie;
import com.bookar.entities.MovieCast;

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
        Movie movie = movieDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return modelMapper.map(movie, MovieResponseDTO.class);
    }

    @Override
    public List<String> getLocationsForMovie(Long movieId) {
        return showDao.findDistinctLocationsByMovieId(movieId);
    }

    @Override
    public MovieDetailDTO getMovieDetailsById(Long id) {
        Movie movie = movieDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        // ✅ Map MovieCast to MovieCastDTO explicitly
        modelMapper.typeMap(MovieCast.class, MovieCastDTO.class);

        return modelMapper.map(movie, MovieDetailDTO.class);
    }
}
