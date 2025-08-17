package com.bookar.dto;

import java.util.List;

import com.bookar.entities.SeatType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutRequestDTO {
  private List<SeatLayout> layout;
  
  @Getter @Setter @NoArgsConstructor @AllArgsConstructor
  public static class SeatLayout {
  	  
    @NotBlank(message = "Row label cannot be blank")
    private String rowLabel;
    
    @Min(value = 1, message = "Seat number must be greater than 0")
    private int seatNumber;
    
    @NotNull(message = "Seat type must not be null")
    private SeatType type;      
  }
  
}

