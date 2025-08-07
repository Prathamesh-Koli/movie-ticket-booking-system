package com.bookar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookar.dao.BookingDao;
import com.bookar.dao.ReservationDao;
import com.bookar.dao.ReservationSeatDao;
import com.bookar.dao.ShowSeatDao;
import com.bookar.entities.Booking;
import com.bookar.entities.Reservation;
import com.bookar.entities.ReservationSeat;
import com.bookar.entities.ShowSeat;
import com.bookar.entities.ReservationStatus;
import com.bookar.entities.SeatStatus;

@Service
@Transactional
public class BookingService {
	@Autowired
	private ReservationDao reservationDao;

	@Autowired
	private ReservationSeatDao reservationSeatDao;

	@Autowired
	private ShowSeatDao showSeatDao;

	@Autowired
	private BookingDao bookingDao;

	/**
	 * Confirms the reservation by:
	 * - Marking seats as BOOKED
	 * - Updating reservation status
	 * - Creating a Booking entry
	 */
	
	public void confirmBooking(Long reservationId, Long userId, String paymentId) {
	    Reservation reservation = reservationDao.findById(reservationId)
	            .orElseThrow(() -> new RuntimeException("Reservation not found"));

	    if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
	        throw new RuntimeException("Reservation is not in pending state");
	    }

	    // Update seat status to BOOKED
	    List<ReservationSeat> reservedSeats = reservationSeatDao.findByReservation(reservation);
	    for (ReservationSeat rs : reservedSeats) {
	        ShowSeat showSeat = rs.getShowSeat();
	        showSeat.setSeatStatus(SeatStatus.BOOKED);
	        showSeatDao.save(showSeat);
	    }

	    // Update reservation status to CONFIRMED
	    reservation.setReservationStatus(ReservationStatus.CONFIRMED);
	    reservationDao.save(reservation);

	    // Create Booking entry
	    Booking booking = new Booking();
	    booking.setUser(reservation.getUser());
	    booking.setReservation(reservation);
	    booking.setPaymentId(paymentId);
	    booking.setBookingTime(LocalDateTime.now());
	    bookingDao.save(booking); 
	}

	/**
	 * Cancels the reservation and reverts seat status to AVAILABLE
	 */
	
	public void cancelReservation(Long reservationId) {
	    Reservation reservation = reservationDao.findById(reservationId)
	            .orElseThrow(() -> new RuntimeException("Reservation not found"));

	    if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
	        return;
	    }

	    // Update seat status to AVAILABLE
	    List<ReservationSeat> reservedSeats = reservationSeatDao.findByReservation(reservation);
	    for (ReservationSeat rs : reservedSeats) {
	        ShowSeat showSeat = rs.getShowSeat();
	        showSeat.setSeatStatus(SeatStatus.AVAILABLE);
	        showSeatDao.save(showSeat);
	    }

	    // Update reservation status to CANCELLED
	    reservation.setReservationStatus(ReservationStatus.CANCELLED);
	    reservationDao.save(reservation);
	}

}