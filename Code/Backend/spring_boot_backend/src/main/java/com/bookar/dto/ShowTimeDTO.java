package com.bookar.dto;

import java.time.LocalTime;

import com.bookar.entities.ShowStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShowTimeDTO {
	private Long showId;
	private String startTime;
	private ShowStatus showStatus;
	private int availableSeats;
}
