package com.example.booking.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Show show;

    private OffsetDateTime bookedAt;
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingItem> items;
}
