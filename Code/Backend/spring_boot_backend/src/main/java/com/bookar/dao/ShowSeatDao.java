package com.bookar.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.bookar.entities.SeatStatus;
import com.bookar.entities.Show;
import com.bookar.entities.ShowSeat;

import jakarta.persistence.LockModeType;

public interface ShowSeatDao extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowAndSeat_Screen_ScreenId(Show show, Long screenId);
    Optional<ShowSeat> findByShowAndSeat_RowLabelAndSeat_SeatNumber(Show show, String rowLabel, int seatNumber);
    int countByShowAndSeatStatusIn(Show show, List<SeatStatus> statuses);
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.showSeatId = :id") 
    Optional<ShowSeat> findByIdForUpdate(Long seatId);
    
}
