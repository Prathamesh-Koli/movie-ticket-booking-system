package com.bookar.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bookar.dao.ScreenDao;
import com.bookar.dao.TheaterDao;
import com.bookar.dto.TheaterRequestDTO;
import com.bookar.dto.TheaterResponseDTO;
import com.bookar.entities.Screen;
import com.bookar.entities.Theater;
import com.bookar.entities.TheaterStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterServiceImpl implements TheaterService {

    private final TheaterDao theaterDao;
    private final ScreenDao screenDao;

    @Override
    public Theater addTheater(TheaterRequestDTO dto) {
        if (dto.getScreenCount() == null || dto.getScreenCount() < 1) {
            throw new IllegalArgumentException("screenCount must be >= 1");
        }

        Theater theater = new Theater();
        theater.setTheaterName(dto.getTheaterName());
        theater.setTheaterLocation(dto.getTheaterLocation());
        theater.setTheaterAddress(dto.getTheaterAddress());
        theater.setScreenCount(dto.getScreenCount());
        theater.setStatus(TheaterStatus.PENDING);

        if (dto.getOwnerId() != null) {
            theater.setOwnerId(dto.getOwnerId());
        }

        Theater savedTheater = theaterDao.save(theater);

        // Auto-create screens
        List<Screen> screenList = new ArrayList<>();
        for (int i = 1; i <= dto.getScreenCount(); i++) {
            Screen screen = new Screen();
            screen.setScreenNumber("Screen " + i);
            screen.setTheater(savedTheater);
            screenList.add(screen);
        }
        screenDao.saveAll(screenList);

        return savedTheater;
    }

    @Override
    public List<Theater> getTheatersByStatus(TheaterStatus status) {
        return theaterDao.findByStatus(status);
    }

    @Override
    public List<Theater> getTheatersByOwnerAndStatus(Long ownerId, TheaterStatus status) {
        return theaterDao.findByOwnerIdAndStatus(ownerId, status);
    }

    @Override
    public List<Theater> getAllTheaters() {
        return theaterDao.findAll();
    }

    @Override
    public Theater updateTheaterStatus(Long theaterId, TheaterStatus status) {
        Theater theater = theaterDao.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        theater.setStatus(status);
        return theaterDao.save(theater);
    }

    @Override
    public void deleteTheater(Long theaterId) {
        if (!theaterDao.existsById(theaterId)) {
            throw new RuntimeException("Theater not found");
        }
        theaterDao.deleteById(theaterId);
    }

    @Override
    public TheaterResponseDTO getTheaterDetails(Long theaterId) {
        Theater theater = theaterDao.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        TheaterResponseDTO dto = new TheaterResponseDTO();
        dto.setTheaterId(theater.getTheaterId());
        dto.setTheaterName(theater.getTheaterName());
        dto.setTheaterLocation(theater.getTheaterLocation());
        dto.setTheaterAddress(theater.getTheaterAddress());
        dto.setScreenCount(theater.getScreenCount());
        dto.setStatus(theater.getStatus().name());

        // Map screens manually to avoid lazy loading issues
        if (theater.getScreens() != null) {
            dto.setScreens(
                    theater.getScreens().stream()
                            .map(screen -> screen.getScreenNumber())
                            .toList()
            );
        }

        return dto;
    }

}
