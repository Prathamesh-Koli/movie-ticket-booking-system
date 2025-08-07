package com.bookar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookar.entities.Seat;

public interface SeatDao extends JpaRepository<Seat, Long> { }
