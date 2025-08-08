package com.bookar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.dao.MovieDao;
import com.bookar.dao.ShowDao;
import com.bookar.dto.ShowDetailsDTO;
import com.bookar.dto.ShowTimeDTO;
import com.bookar.dto.TheaterShowDTO;
import com.bookar.entities.Show;
import com.bookar.entities.Theater;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {

    private ShowDao showDao;

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

	
}

