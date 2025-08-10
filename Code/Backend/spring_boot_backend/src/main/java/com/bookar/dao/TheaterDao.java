package com.bookar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookar.entities.Theater;

public interface TheaterDao extends JpaRepository<Theater, Long> {
   
}
