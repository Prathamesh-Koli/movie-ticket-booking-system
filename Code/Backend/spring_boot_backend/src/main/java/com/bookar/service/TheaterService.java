package com.bookar.service;

import java.util.List;

import com.bookar.dto.LayoutRequestDTO.SeatLayout;
import com.bookar.dto.SeatLayoutResponseDTO;
import com.bookar.dto.TheaterInfoDTO;

public interface TheaterService {
	TheaterInfoDTO getTheaterById(Long theaterId);

	void saveLayoutForAllScreens(Long theaterId, List<SeatLayout> layout);

	List<SeatLayoutResponseDTO> getSavedLayout(Long theaterId);
}