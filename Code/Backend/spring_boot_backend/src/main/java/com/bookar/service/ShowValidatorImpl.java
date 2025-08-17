package com.bookar.service;

import java.time.LocalDate;
import java.time.LocalTime;


import org.springframework.stereotype.Service;

import com.bookar.dao.ShowDao;
import com.bookar.entities.Show;
import com.bookar.entities.ShowStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShowValidatorImpl implements ShowValidator{
	private final ShowDao showDao;
	@Override
	public void validateShowSchedulable(Show show) {

		
		LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        LocalDate showDate = show.getShowDate();
        LocalTime startTime = show.getStartTime();
        LocalTime endTime = startTime.plusHours(3); 
       
        if (today.isBefore(showDate) || (today.isEqual(showDate) && nowTime.isBefore(startTime))) {
            show.setShowStatus(ShowStatus.SCHEDULED);
        } else if (today.isEqual(showDate) && !nowTime.isAfter(endTime)) {
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
