package com.bookar.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookar.entities.Show;

public interface ShowDao extends JpaRepository<Show, Long> {

	@Query("SELECT DISTINCT s.screen.theater.theaterLocation " +
	           "FROM Show s " +
	           "WHERE s.movie.id = :movieId")
	List<String> findDistinctLocationsByMovieId(@Param("movieId") Long movieId);
	
	@Query("""
		    SELECT s FROM Show s
		    WHERE s.movie.id = :movieId
		      AND s.showDate = :date
		      AND s.screen.theater.theaterLocation LIKE %:location%
		""")
		List<Show> findShowsByMovieDateLocation(
		    @Param("movieId") Long movieId,
		    @Param("date") LocalDate date,
		    @Param("location") String location
		);
}
