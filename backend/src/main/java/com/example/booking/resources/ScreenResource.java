package com.example.booking.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.booking.services.ScreenService;
import com.example.booking.entities.Screen;

@Path("/screens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreenResource {

    @Inject
    private ScreenService screenService;

    @GET
    public Response listScreens() {
        return Response.ok(screenService.findAll()).build();
    }

    @POST
    public Response createScreen(Screen s) {
        Screen created = screenService.create(s);
        return Response.status(201).entity(created).build();
    }
}
