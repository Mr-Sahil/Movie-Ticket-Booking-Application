package com.example.booking.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.booking.services.MovieService;
import com.example.booking.entities.Movie;
import java.util.List;
import java.util.stream.Collectors;
import com.example.booking.dto.MovieDto;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieService movieService;

    @GET
    public Response listMovies() {
        List<MovieDto> dtos = movieService.findAll().stream().map(m -> new MovieDto(m.getId(), m.getTitle())).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @POST
    public Response createMovie(MovieDto dto) {
        Movie m = new Movie();
        m.setTitle(dto.getTitle());
        Movie created = movieService.create(m);
        return Response.status(201).entity(new MovieDto(created.getId(), created.getTitle())).build();
    }
}
