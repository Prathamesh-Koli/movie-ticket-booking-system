package com.bookar.service;

import com.bookar.dto.DashboardStatsDTO;
import com.bookar.dto.UserDTO;
import com.bookar.entities.Booking;
import com.bookar.entities.User;
import com.bookar.dao.*;
import com.bookar.service.AdminService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.bookar.entities.*;
@Service
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;
    private final MovieDao movieDao;
    private final TheatreDao theatreDao;
    private final BookingDao bookingDao;

    public AdminServiceImpl(UserDao userDao, MovieDao movieDao,
                            TheatreDao theatreDao, BookingDao bookingDao) {
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.theatreDao = theatreDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public DashboardStatsDTO fetchDashboardStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setTotalMovies((int) movieDao.count());
        dto.setTotalUsers((int) userDao.count());
        dto.setTotalTheaters((int) theatreDao.count());
        dto.setTotalBookings((int) bookingDao.count());
        dto.setTodayRevenue(bookingDao.getTodayRevenue(LocalDate.now()));
        dto.setOccupancyRate(bookingDao.calculateOccupancyRate());
        return dto;
    }

    @Override
    public List<UserDTO> fetchAllUsers() {
        return userDao.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getFirstname() + " " + user.getLastname());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getMobile_no());
            dto.setRegistrationDate(user.getCreatedAt());
            dto.setTotalBookings(bookingDao.countByUserId(user.getId()));
            dto.setStatus(user.getStatus()); // Use status field directly
            return dto;
        }).collect(Collectors.toList());
    }

    
    @Override
    public UserDTO toggleUserStatus(Long userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        String newStatus = user.getStatus().equalsIgnoreCase("active") ? "blocked" : "active";
        
        userDao.updateStatus(userId, newStatus); // Custom update method
        
        // Update the user object locally as well
        user.setStatus(newStatus);

        return fetchAllUsers().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Updated user not found"));
    }


}
