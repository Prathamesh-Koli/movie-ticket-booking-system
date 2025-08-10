package com.bookar.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bookar.entities.Screen;

public interface ScreenDao extends JpaRepository<Screen, Long> {
	Optional<Screen> findByTheater_TheaterIdAndScreenNumber(Long theaterId, String screenNumber);


}
