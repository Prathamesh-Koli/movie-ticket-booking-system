package com.bookar.service;

import java.time.LocalDate;
import java.util.List;

import com.bookar.dto.ShowDetailsDTO;
import com.bookar.dto.TheaterShowDTO;

public interface ShowService {
	List<TheaterShowDTO> getTheatersWithShows(Long movieId, LocalDate date, String location);
	ShowDetailsDTO getShowDetails(Long showId);
}
