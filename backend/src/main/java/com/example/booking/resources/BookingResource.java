package com.example.booking.resources;

import com.example.booking.dao.ShowDao;
import com.example.booking.dao.UserDao;
import com.example.booking.dto.BookingRequest;
import com.example.booking.entities.Booking;
import com.example.booking.entities.Show;
import com.example.booking.entities.User;
import com.example.booking.services.BookingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    BookingService bookingService;

    @Inject
    UserDao userDao;

    @Inject
    ShowDao showDao;

    @POST
    public Response create(BookingRequest req) {
        User user = userDao.findById(req.getUserId()).orElseThrow(() -> new WebApplicationException("User not found", 404));
        Show show = showDao.findById(req.getShowId()).orElseThrow(() -> new WebApplicationException("Show not found", 404));

        try {
            Booking b = bookingService.bookSeats(user, show, req.getShowSeatIds());
            return Response.status(201).entity(b).build();
        } catch (RuntimeException ex) {
            return Response.status(409).entity(Map.of("error", ex.getMessage())).build();
        }
    }
}

