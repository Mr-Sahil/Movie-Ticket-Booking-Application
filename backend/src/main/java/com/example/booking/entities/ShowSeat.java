package com.example.booking.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show_seats", uniqueConstraints = @UniqueConstraint(columnNames = {"show_id","seat_id"}))
public class ShowSeat implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @Version
    private long version;

    private boolean booked;
    private String lockedBy;
    private OffsetDateTime lockExpiresAt;
}
