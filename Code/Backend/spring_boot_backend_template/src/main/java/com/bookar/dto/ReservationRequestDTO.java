package com.bookar.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ReservationRequestDTO {
    private Long showId;
    private Long userId;
    private List<Long> showSeatIds;  
}




