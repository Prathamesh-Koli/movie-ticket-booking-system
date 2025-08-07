package com.bookar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.custom_exceptions.ResourceNotFoundException;
import com.bookar.dao.ShowDao;
import com.bookar.dao.ShowSeatDao;
import com.bookar.dto.SeatResponseDTO;
import com.bookar.entities.Seat;
import com.bookar.entities.Show;
import com.bookar.entities.ShowSeat;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
	private final ShowDao showDao;
	private final ShowSeatDao showSeatDao;
	private final ReservationService reservationService;
	
	
	@Override
	public List<SeatResponseDTO> getSeatsForShow(Long showId, Long theaterId) {
		 reservationService.expireOldReservations();
		
		 Show show = showDao.findById(showId)
		            .orElseThrow(() -> new ResourceNotFoundException("Show not found"));
		        List<ShowSeat> showSeat = showSeatDao.findByShowAndSeat_Screen_ScreenId(show, theaterId);

		        return showSeat.stream().map(ss -> {
		        	SeatResponseDTO dto = new SeatResponseDTO();
		        	dto.setShowSeatId(ss.getShowSeatId());
		            Seat s = ss.getSeat();
		            dto.setId(s.getRowLabel() + s.getSeatNumber());
		            dto.setRow(s.getRowLabel());
		            dto.setNumber(s.getSeatNumber());
		            dto.setType(s.getType());
		            dto.setPrice(s.getPrice());
		            dto.setStatus(ss.getSeatStatus().name().toLowerCase());
		            return dto;
		        }).collect(Collectors.toList());
    }
	

}
