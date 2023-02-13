package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.mapper.UserMapper;
import com.lambda.appointment.notes.model.User;
import com.lambda.appointment.notes.service.GoogleAuthService;
import com.lambda.appointment.notes.service.UserService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @Inject
    GoogleAuthService authService;

    //@Inject
    //HttpServletRequest request;


    @POST
    public Response addUser(UserDTO userDTO) {
        //validateIntegritityToken();
        userService.addUser(userDTO);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    public void updateUser(UserDTO userDTO) {
        //validateIntegritityToken();
        userService.updateUser(UserMapper.toEntity(userDTO));
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public void removeUser(@PathParam Long id) {
        //validateIntegritityToken();
        userService.removeUser(id);
    }

    @GET
    public List<UserDTO> allUsers(){
        //validateIntegritityToken();
        return userService.allUsers().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

  /**
      public void validateIntegritityToken(){
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        if (!authService.isTokenValid(accessToken)) {
            accessToken = authService.renewToken(accessToken);
            session.setAttribute("accessToken", accessToken);
        }
    }
   */

}

