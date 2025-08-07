package com.bookar.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bookar.dto.BookingDTO;
import com.bookar.entities.User;
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

	public Long confirmBooking(Long reservationId, Long userId, String paymentId) {
	    Reservation reservation = reservationDao.findById(reservationId)
	        .orElseThrow(() -> new RuntimeException("Reservation not found"));

	    if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
	        throw new RuntimeException("Reservation is not in pending state");
	    }

	    List<ReservationSeat> reservedSeats = reservationSeatDao.findByReservation(reservation);
	    for (ReservationSeat rs : reservedSeats) {
	        ShowSeat showSeat = rs.getShowSeat();
	        showSeat.setSeatStatus(SeatStatus.BOOKED);
	        showSeatDao.save(showSeat);
	    }

	    reservation.setReservationStatus(ReservationStatus.CONFIRMED);
	    reservationDao.save(reservation);

	    Booking booking = new Booking();
	    booking.setBookingTime(LocalDateTime.now());
	    booking.setPaymentId(paymentId);
	    booking.setUser(reservation.getUser());
	    booking.setReservation(reservation);

	    Booking savedBooking = bookingDao.save(booking);
	    return savedBooking.getBookingId();
	}

	public void cancelReservation(Long reservationId) {
	    Reservation reservation = reservationDao.findById(reservationId)
	        .orElseThrow(() -> new RuntimeException("Reservation not found"));

	    if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
	        return;
	    }

	    List<ReservationSeat> reservedSeats = reservationSeatDao.findByReservation(reservation);
	    for (ReservationSeat rs : reservedSeats) {
	        ShowSeat showSeat = rs.getShowSeat();
	        showSeat.setSeatStatus(SeatStatus.AVAILABLE);
	        showSeatDao.save(showSeat);
	    }

	    reservation.setReservationStatus(ReservationStatus.CANCELLED);
	    reservationDao.save(reservation);
	}

	public Booking getBookingById(Long bookingId) {
	    Booking booking = bookingDao.findById(bookingId)
	        .orElseThrow(() -> new RuntimeException("Booking not found"));

	    // Force initialization of lazy fields
	    booking.getReservation().getReservationId();
	    booking.getUser().getId();
	    return booking;
	}

	// ✅ New method to return DTO safely
	public BookingDTO getBookingDTOById(Long bookingId) {
	    Booking booking = bookingDao.findById(bookingId)
	        .orElseThrow(() -> new RuntimeException("Booking not found"));

	    return new BookingDTO(
	        booking.getBookingId(),
	        booking.getPaymentId(),
	        booking.getBookingTime(),
	        booking.getReservation().getReservationId(),
	        booking.getUser().getId()
	    );
	}
}