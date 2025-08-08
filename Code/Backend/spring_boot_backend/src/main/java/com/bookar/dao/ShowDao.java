package com.bookar.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookar.dto.ShowDetailsDTO;
import com.bookar.dto.TheatreDashboardDTO;
import com.bookar.entities.Show;
import com.bookar.entities.ShowStatus;

public interface ShowDao extends JpaRepository<Show, Long> {

	@Query("SELECT DISTINCT s.screen.theater.theaterLocation " + "FROM Show s " + "WHERE s.movie.id = :movieId")
	List<String> findDistinctLocationsByMovieId(@Param("movieId") Long movieId);

	@Query("""
			    SELECT s FROM Show s
			    WHERE s.movie.id = :movieId
			      AND s.showDate = :date
			      AND s.screen.theater.theaterLocation LIKE %:location%
			""")
	List<Show> findShowsByMovieDateLocation(@Param("movieId") Long movieId, @Param("date") LocalDate date,
			@Param("location") String location);

	@Query("SELECT new com.bookar.dto.ShowDetailsDTO( "
			+ "t.theaterName, t.theaterAddress, s.screenNumber, sh.showDate, sh.startTime) " + "FROM Show sh "
			+ "JOIN sh.screen s " + "JOIN s.theater t " + "WHERE sh.showId = :showId")
	ShowDetailsDTO getShowDetailsByShowId(@Param("showId") Long showId);

	@Query(value = """
		SELECT sh.show_id AS showId, m.title AS movieTitle, t.theater_name AS theaterName, s.screen_no AS screenNumber,
            sh.show_date AS showDate, sh.start_time AS startTime, sh.show_status AS showStatus,
            (SELECT COUNT(*) FROM seats seat WHERE seat.screen_id = s.screen_id) AS totalSeats,
            (SELECT COUNT(*) FROM show_seats ss JOIN reservations r ON r.show_id = ss.show_id 
             JOIN reservation_seats rs ON rs.show_seat_id = ss.show_seat_id
             WHERE ss.show_id = sh.show_id AND ss.seat_status = 'BOOKED' AND r.reservation_status = 'CONFIRMED') AS bookedSeats,
            (SELECT SUM(price) FROM show_seat_type_prices price JOIN show_seats ss ON price.show_id = ss.show_id
             JOIN seats seat ON seat.seat_id = ss.seat_id JOIN reservation_seats rs ON rs.show_seat_id = ss.show_seat_id
             JOIN reservations r ON r.reservation_id = rs.reservation_id WHERE ss.show_id = sh.show_id AND r.reservation_status = 'CONFIRMED'
             AND seat.seat_type = price.seat_type) AS revenue FROM shows sh
        JOIN movies m ON sh.movie_id = m.movie_id JOIN screens s ON s.screen_id = sh.screen_id JOIN theaters t ON t.theater_id = s.theater_id
        WHERE sh.show_status IN ('ACTIVE', 'SCHEDULED') AND t.owner_id = :ownerId """, nativeQuery = true)
	List<Object[]> findShowStatsByOwner(@Param("ownerId") Long ownerId);

	@Query(value = """
			SELECT

			(SELECT COUNT(*) FROM theaters WHERE owner_id = :ownerId AND status = 'PENDING') AS pendingTheaters,

			(SELECT COUNT(*) FROM theaters WHERE owner_id = :ownerId AND status = 'APPROVED') AS approvedTheaters,

			(SELECT COUNT(*) FROM theaters WHERE owner_id = :ownerId) AS totalTheaters,

			(SELECT COUNT(DISTINCT sh.show_id) FROM shows sh JOIN screens sc ON sh.screen_id = sc.screen_id JOIN theaters t
			ON sc.theater_id = t.theater_id WHERE t.owner_id = :ownerId AND sh.show_status = 'ACTIVE') AS activeShows,

			(SELECT COUNT(DISTINCT sh.show_id) FROM shows sh JOIN screens sc ON sh.screen_id = sc.screen_id
			 JOIN theaters t ON sc.theater_id = t.theater_id WHERE t.owner_id = :ownerId AND sh.show_status = 'SCHEDULED') AS scheduledShows,

			(SELECT COUNT(DISTINCT r.reservation_id) FROM reservations r JOIN shows sh ON r.show_id = sh.show_id
			 JOIN screens sc ON sh.screen_id = sc.screen_id JOIN theaters t ON sc.theater_id = t.theater_id
			 WHERE t.owner_id = :ownerId AND DATE(r.reserved_at) = CURDATE() AND r.reservation_status = 'CONFIRMED') AS todaysBookings,

			(SELECT COALESCE(SUM(COALESCE(sstp.price, s.seat_price)), 0) FROM reservations r
			 JOIN reservation_seats rs ON r.reservation_id = rs.reservation_id
			 JOIN show_seats ss ON rs.show_seat_id = ss.show_seat_id JOIN seats s ON ss.seat_id = s.seat_id
			 JOIN shows sh ON ss.show_id = sh.show_id LEFT JOIN show_seat_type_prices sstp ON sh.show_id = sstp.show_id AND s.seat_type = sstp.seat_type
			 JOIN screens sc ON sh.screen_id = sc.screen_id JOIN theaters t ON sc.theater_id = t.theater_id
			 WHERE t.owner_id = :ownerId AND DATE(r.reserved_at) = CURDATE() AND r.reservation_status = 'CONFIRMED') AS todaysRevenue;
			  		""", nativeQuery = true)
	TheatreDashboardDTO getOwnerDashboardStats(@Param("ownerId") Long ownerId);

	@Modifying
	@Query("UPDATE Show s SET s.showStatus = 'EXPIRED' WHERE s.showDate < CURRENT_DATE AND s.showStatus != 'EXPIRED'")
	int expireOldShows();

	@Modifying
    @Query("UPDATE Show s SET s.showStatus = :status WHERE s.id = :showId")
	void updateShowStatus(Long showId, ShowStatus status);

}
