package com.bookar.dao;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookar.entities.Booking;

@Repository
public interface BookingDao extends JpaRepository<Booking, Long> {

	Booking save(Booking booking);
	@Query("SELECT COALESCE(SUM(s.price), 0) FROM Booking b " +
		       "JOIN b.reservation r " +
		       "JOIN r.reservationSeats rs " +
		       "JOIN rs.showSeat ss " +
		       "JOIN ss.seat s " +
		       "WHERE DATE(b.bookingTime) = :date")
		double getTodayRevenue(@Param("date") LocalDate date);

		@Query("SELECT (COUNT(ss) * 100.0) / (SELECT COUNT(s2) FROM ShowSeat s2) FROM ShowSeat ss WHERE ss.seatStatus = 'BOOKED'")
		double calculateOccupancyRate();

		int countByUserId(Long userId);

}