package com.example.booking.dto;

import com.example.booking.entities.Show;
import com.example.booking.entities.ShowSeat;

public class Mapper {
    public static ShowDto toShowDto(Show s) {
        ShowDto dto = new ShowDto();
        dto.setId(s.getId());
        MovieDto md = new MovieDto(s.getMovie().getId(), s.getMovie().getTitle());
        dto.setMovie(md);
        dto.setScreenId(s.getScreen().getId());
        dto.setScreenName(s.getScreen().getName());
        dto.setStartTime(s.getStartTime());
        return dto;
    }

    public static ShowSeatDto toShowSeatDto(ShowSeat ss) {
        return new ShowSeatDto(
                ss.getId(),
                ss.getShow().getId(),
                ss.getSeat().getSeatNumber(),
                ss.isBooked()
        );
    }
}
