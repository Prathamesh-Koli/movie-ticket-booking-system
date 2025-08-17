package com.bookar.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.dao.MovieDao;
import com.bookar.dao.ScreenDao;
import com.bookar.dao.SeatDao;
import com.bookar.dao.ShowDao;
import com.bookar.dao.ShowSeatDao;
import com.bookar.dao.ShowSeatTypePriceDao;
import com.bookar.entities.Show;
import com.bookar.entities.ShowStatus;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ShowValidatorImpl implements ShowValidator {
    private final ShowDao showDao;

    @Override
    public void validateShowSchedulable(Show show) {

        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        LocalDate showDate = show.getShowDate();
        LocalTime startTime = show.getStartTime();
        LocalTime endTime = startTime.plusHours(3); 

    
        LocalDateTime now = LocalDateTime.of(today, nowTime);
        LocalDateTime showStart = LocalDateTime.of(showDate, startTime);
        LocalDateTime showEnd = LocalDateTime.of(showDate, endTime);

        
        if (now.isBefore(showStart)) {
           
            show.setShowStatus(ShowStatus.SCHEDULED);
        } else if (!now.isAfter(showEnd)) {
                       show.setShowStatus(ShowStatus.ACTIVE);
        } else {
           
            show.setShowStatus(ShowStatus.EXPIRED);
        }

        
        showDao.save(show);

       
        if (show.getShowStatus() != ShowStatus.SCHEDULED) {
            throw new IllegalStateException("Cannot book seats: show is not scheduled");
        }
    }
}

