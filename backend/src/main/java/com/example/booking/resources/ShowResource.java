package com.example.booking.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.booking.dao.ShowDao;
import com.example.booking.dao.SeatDao;
import com.example.booking.dto.Mapper;
import com.example.booking.dto.ShowDto;
import com.example.booking.dto.ShowSeatDto;
import com.example.booking.entities.ShowSeat;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Path("/shows")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShowResource {

    @Inject
    private ShowDao showDao;

    @Inject
    private SeatDao seatDao;

    @GET
    public Response listShows() {

        List<ShowDto> dtos = showDao.findAll().stream().map(Mapper::toShowDto).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    public Response getShow(@PathParam("id") Long id) {
        return showDao.findById(id)
                .map(s -> Response.ok(Mapper.toShowDto(s)).build())
                .orElse(Response.status(404).entity(java.util.Map.of("error", "Show not found")).build());
    }

    @GET
    @Path("/{id}/seats")
    public Response getSeats(@PathParam("id") Long id) {
        List<ShowSeat> seats = seatDao.findByShowId(id);
        List<ShowSeatDto> dto = seats.stream().map(Mapper::toShowSeatDto).collect(Collectors.toList());
        return Response.ok(dto).build();
    }
}

