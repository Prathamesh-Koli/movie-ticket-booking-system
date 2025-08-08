package com.bookar.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bookar.entities.ShowSeat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
	private Long reservationId;
    private Long userId;
    private Long showId;
    private Long theaterId;
    private List<Long> showSeatIds;

    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private String reservationStatus;

    private BigDecimal ticketAmount;
    private BigDecimal convenienceFee;
    private BigDecimal totalPayable;

}
