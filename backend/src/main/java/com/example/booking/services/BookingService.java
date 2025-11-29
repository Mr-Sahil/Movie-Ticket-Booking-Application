package com.example.booking.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.example.booking.dao.ShowSeatDao;
import com.example.booking.dao.BookingDao;
import com.example.booking.entities.*;

import java.time.OffsetDateTime;
import java.util.*;

@Stateless
public class BookingService {

    @Inject
    private ShowSeatDao showSeatDao;

    @Inject
    private BookingDao bookingDao;

    // Attempt to reserve seats atomically
    @Transactional
    public Booking bookSeats(User user, Show show, List<Long> seatIds) {
        // 1. load showSeat rows with pessimistic lock
        List<ShowSeat> locked = new ArrayList<>();
        for (Long seatId : seatIds) {
            Optional<ShowSeat> opt = showSeatDao.findByShowAndSeat(show.getId(), seatId);
            if (opt.isEmpty()) throw new RuntimeException("Seat not available");
            ShowSeat s = showSeatDao.findWithPessimisticLock(opt.get().getId());

            if (s.isBooked()) throw new RuntimeException("Seat already booked");
            // optionally check lock expiration and lock ownership
            s.setBooked(true);
            locked.add(s);
        }

        // 2. persist booking + items
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookedAt(OffsetDateTime.now());
        booking.setStatus("CONFIRMED");

        List<BookingItem> items = new ArrayList<>();
        for (ShowSeat s : locked) {
            BookingItem item = new BookingItem();
            item.setBooking(booking);
            item.setSeat(s.getSeat());
            item.setPrice(100.0); // price logic stub
            items.add(item);
        }
        booking.setItems(items);

        Booking created = bookingDao.create(booking);

        // 3. update show seats
        for (ShowSeat s : locked) showSeatDao.update(s);

        return created;
    }
}
