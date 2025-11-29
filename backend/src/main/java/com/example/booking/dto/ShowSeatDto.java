package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDto {
    public Long id;
    public Long showId;
    public String seatId;
    public boolean booked;
}

