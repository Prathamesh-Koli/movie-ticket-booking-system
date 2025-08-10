package com.bookar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.custom_exceptions.ResourceNotFoundException;
import com.bookar.dao.SeatDao;
import com.bookar.dao.TheaterDao;
import com.bookar.dto.LayoutRequestDTO;
import com.bookar.dto.SeatLayoutResponseDTO;
import com.bookar.dto.TheaterInfoDTO;
import com.bookar.entities.Screen;
import com.bookar.entities.Seat;
import com.bookar.entities.Theater;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TheaterServiceImpl implements TheaterService {
    private final TheaterDao theaterDao;
    private final SeatDao seatDao;

    @Override
    public TheaterInfoDTO getTheaterById(Long theaterId) {
        Theater theater = theaterDao.findById(theaterId)
            .orElseThrow(() -> new ResourceNotFoundException("Theater not found: " + theaterId));

        
        List<TheaterInfoDTO.ScreenInfo> screenInfos = theater.getScreens().stream()
            .map(s -> new TheaterInfoDTO.ScreenInfo(
                s.getScreenId(),
                s.getScreenNumber()
            ))
            .toList();

        TheaterInfoDTO dto = new TheaterInfoDTO();
        dto.setTheaterId(theater.getTheaterId());
        dto.setTheaterName(theater.getTheaterName());
        dto.setTheaterLocation(theater.getTheaterLocation());
        dto.setTheaterAddress(theater.getTheaterAddress());
        dto.setScreens(screenInfos);

        return dto;
    }
    
    @Override
    public void saveLayoutForAllScreens(Long theaterId, List<LayoutRequestDTO.SeatLayout> layout) {
        var theater = theaterDao.findById(theaterId)
          .orElseThrow(() -> new ResourceNotFoundException("Theater not found: " + theaterId));

       
        for (Screen screen : theater.getScreens()) {
          
          seatDao.deleteByScreen_ScreenId(screen.getScreenId());

          for (LayoutRequestDTO.SeatLayout sl : layout) {
        	  
        	  if (sl.getRowLabel() == null) {
        	        System.out.println("Null row_label found for seatNumber: " + sl.getSeatNumber());
        	    }
            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setRowLabel(sl.getRowLabel());
            seat.setSeatNumber(sl.getSeatNumber());
            seat.setType(sl.getType());
            seatDao.save(seat);
          }
        }
      }
    
    
    @Override
    public List<SeatLayoutResponseDTO> getSavedLayout(Long theaterId) {
        Theater theater = theaterDao.findById(theaterId)
            .orElseThrow(() -> new ResourceNotFoundException("Theater not found: " + theaterId));

        List<Screen> screens = theater.getScreens();
        if (screens.isEmpty()) {
            throw new ResourceNotFoundException("No screens found for theater: " + theaterId);
        }

        
        Long screenId = screens.get(0).getScreenId();
        List<Seat> seats = seatDao.findByScreen_ScreenId(screenId);

      
        return seats.stream().map(seat -> new SeatLayoutResponseDTO(
            seat.getRowLabel(),
            seat.getSeatNumber(),
            seat.getType()
        )).collect(Collectors.toList());
    }
    
    
}