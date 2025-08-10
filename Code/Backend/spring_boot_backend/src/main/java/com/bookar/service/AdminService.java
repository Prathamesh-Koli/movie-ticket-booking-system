package com.bookar.service;

import com.bookar.dto.*;
import java.util.List;

public interface AdminService {
	DashboardStatsDTO fetchDashboardStats();
    List<UserDTO> fetchAllUsers();
    UserDTO toggleUserStatus(Long userId);
}
