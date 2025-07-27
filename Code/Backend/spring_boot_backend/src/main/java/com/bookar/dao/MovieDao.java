package com.bookar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookar.entities.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

}
