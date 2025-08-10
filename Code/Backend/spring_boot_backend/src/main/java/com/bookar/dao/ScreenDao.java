package com.bookar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookar.entities.Screen;

@Repository
public interface ScreenDao extends JpaRepository<Screen, Long> {
	List<Screen> findByTheater_TheaterId(Long theaterId);
}
