package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.GoogleAuthConfigDTO;
import com.lambda.appointment.notes.mapper.GoogleAuthConfigMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigController {

    @Inject
    GoogleAuthConfig googleAuthConfig;

    @GET
    @Path("google")
    public GoogleAuthConfigDTO getGoogleAuthConfig() {
        GoogleAuthConfigDTO googleAuthConfigDTO = GoogleAuthConfigMapper.toDTO(googleAuthConfig);
        return googleAuthConfigDTO;
    }

}
