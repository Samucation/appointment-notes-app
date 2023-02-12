package com.lambda.appointment.notes;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/users")
public class Controller {

    @Inject
    UserService userService;

    @Operation(summary = "Adiciona um novo usuário")
    @POST
    public void addUser(UserDTO userDTO) {
        userService.addUser(userDTO);
    }

    @Operation(summary = "Remove um usuário")
    @DELETE
    @Path("/{id}")
    public void removeUser(@PathParam("id") Long id) {
        userService.removeUser(id);
    }

}
