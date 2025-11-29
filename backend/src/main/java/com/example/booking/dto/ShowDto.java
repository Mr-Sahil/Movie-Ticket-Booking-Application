package com.example.booking.dto;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDto {
    private Long id;
    private MovieDto movie;
    private Long screenId;
    private String screenName;
    private OffsetDateTime startTime;
}
