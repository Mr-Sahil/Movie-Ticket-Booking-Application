package com.example.booking.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.booking.services.UserService;
import com.example.booking.entities.User;
import com.example.booking.dto.UserDto;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Long id) {
        return userService.findById(id)
                .map(u -> Response.ok(new UserDto(u.getId(), u.getName(), u.getEmail())).build())
                .orElse(Response.status(404).entity(Map.of("error","User not found")).build());
    }

    @POST
    public Response createUser(UserDto userDto) {
        // NOTE: password handling omitted here – in production accept password and hash
        User u = new User();
        u.setName(userDto.getName());
        u.setEmail(userDto.getEmail());
        User created = userService.create(u);
        return Response.status(201).entity(new UserDto(created.getId(), created.getName(), created.getEmail())).build();
    }
}
