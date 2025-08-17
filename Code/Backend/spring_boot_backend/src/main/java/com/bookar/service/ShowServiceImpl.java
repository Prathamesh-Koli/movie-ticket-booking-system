package com.bookar.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.custom_exceptions.ResourceNotFoundException;
import com.bookar.dao.ShowDao;
import com.bookar.dao.ShowSeatTypePriceDao;
import com.bookar.dto.ShowDetailsDTO;
import com.bookar.dto.ShowTimeDTO;
import com.bookar.dto.TheaterShowDTO;
import com.bookar.dto.TheaterShowManageDTO;
import com.bookar.entities.Show;
import com.bookar.entities.ShowStatus;
import com.bookar.entities.Theater;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {

    private ShowDao showDao;
    private MovieDao movieDao;
    private ScreenDao screenDao;
    private SeatDao seatDao;
    private ShowSeatDao showSeatDao;
    private ShowSeatTypePriceDao showSeatTypePriceDao;

    @Override
    public List<TheaterShowDTO> getTheatersWithShows(Long movieId, LocalDate date, String location) {
        List<Show> shows = showDao.findShowsByMovieDateLocation(movieId, date, location);

        Map<Theater, List<Show>> grouped = shows.stream()
            .collect(Collectors.groupingBy(s -> s.getScreen().getTheater()));

        List<TheaterShowDTO> result = new ArrayList<>();

        for (Map.Entry<Theater, List<Show>> entry : grouped.entrySet()) {
            Theater theater = entry.getKey();
            List<Show> theaterShows = entry.getValue();

            String screenName = theaterShows.get(0).getScreen().getScreenNumber();

            List<ShowTimeDTO> showTimes = theaterShows.stream()
                .map(s -> new ShowTimeDTO(s.getShowId(), s.getStartTime().toString()))
                .toList();

            result.add(new TheaterShowDTO(
                theater.getTheaterId(),
                theater.getTheaterName(),
                theater.getTheaterLocation(),
                theater.getTheaterAddress(),
                screenName,
                showTimes
            ));
        }
        return result;
    }

	@Override
	public ShowDetailsDTO getShowDetails(Long showId) {
		return showDao.getShowDetailsByShowId(showId);
	}

	@Override
	public List<TheaterShowManageDTO> getShowManagementDetails(Long ownerId) {
		int res = showDao.expireOldShows();
		List<Object[]> results = showDao.findShowStatsByOwner(ownerId);

        List<TheaterShowManageDTO> dtos = new ArrayList<>();

        for (Object[] row : results) {
        	TheaterShowManageDTO dto = new TheaterShowManageDTO();
        	dto.setShowId(((Number) row[0]).longValue());
        	dto.setMovieTitle((String) row[1]);
        	dto.setTheaterName((String) row[2]);
        	dto.setScreenNumber((String) row[3]);
        	dto.setShowDate(((Date) row[4]).toLocalDate());
        	dto.setStartTime(((Time) row[5]).toLocalTime());
        	dto.setShowStatus((String) row[6]);
        	dto.setTotalSeats(((Number) row[7]).intValue());
        	dto.setBookedSeats(((Number) row[8]).intValue());
        	dto.setRevenue(row[9] != null ? ((Number) row[9]).doubleValue() : 0.0);

        	// occupancy rate calculation
        	int total = dto.getTotalSeats(), booked = dto.getBookedSeats();
        	dto.setOccupancyRate(total == 0 ? 0.0 : (booked * 100.0 / total));

            // Fetch seat type prices for the show
            Map<String, Double> prices = showSeatTypePriceDao.findPricesByShowId(dto.getShowId());
            dto.setSeatTypePrices(prices);

            dtos.add(dto);
        }

        return dtos;
	}

	@Override
    public void deleteShow(Long showId) {
        Show show = showDao.findById(showId)
            .orElseThrow(() -> new ResourceNotFoundException("Show not found with ID: " + showId));
        show.setShowStatus(ShowStatus.EXPIRED);
    }

	@Override
	public void activateShow(Long showId) {
	    showDao.updateShowStatus(showId, ShowStatus.ACTIVE);
	}

	
	@Override
	public void deactivateShow(Long showId) {
	    showDao.updateShowStatus(showId, ShowStatus.SCHEDULED);
	}

    public void createShowWithLayout(Long theaterId, CreateShowDTO dto) {
		  
		  Movie movie = movieDao.findById(dto.getMovieId()).orElseThrow(()->new ResourceNotFoundException("Movie Not Found"));
		  Screen screen = screenDao.findById(dto.getScreenId()).orElseThrow(()-> new ResourceNotFoundException("Screen Not Found"));

		 
		  Show show = new Show();
		  show.setMovie(movie);
		  show.setScreen(screen);
		  show.setShowDate(dto.getShowDate());
		  show.setStartTime(dto.getShowTime());
		  show.setShowStatus(ShowStatus.SCHEDULED); 
		  showDao.save(show);

		  
		  dto.getSeatPrices().forEach((type, price) -> {
		    ShowSeatTypePrice p = new ShowSeatTypePrice();
		    p.setShow(show);
		    p.setSeatType(type);
		    p.setPrice(price);
		    priceDao.save(p);
		  });

		
		  List<Seat> seats = seatDao.findByScreen_ScreenId(screen.getScreenId());
		  for (Seat s : seats) {
		    ShowSeat ss = new ShowSeat();
		    ss.setShow(show);
		    ss.setSeat(s);
		    ss.setSeatStatus(SeatStatus.AVAILABLE);
		    showSeatDao.save(ss);
		  }
		}

}

