package com.bookar.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookar.entities.Booking;

@Repository
public interface BookingDao extends JpaRepository<Booking, Long> {

	Booking save(Booking booking);
}