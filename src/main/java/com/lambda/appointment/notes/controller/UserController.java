package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.mapper.UserMapper;
import com.lambda.appointment.notes.service.UserService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@SessionScoped
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @RolesAllowed("Aplicativo")
    @POST
    public Response addUser(UserDTO userDTO) {
        userService.addUser(userDTO);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    public void updateUser(UserDTO userDTO) {
        userService.updateUser(UserMapper.toEntity(userDTO));
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public void removeUser(@PathParam Long id) {
        userService.removeUser(id);
    }

    @GET
    @PermitAll
    public List<UserDTO> allUsers(){
        return userService.allUsers().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

}

