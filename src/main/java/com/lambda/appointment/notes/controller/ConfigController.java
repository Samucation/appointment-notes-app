package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.config.ConfigEnvironment;
import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.ConfigEnvironmentDTO;
import com.lambda.appointment.notes.dto.GoogleAuthConfigDTO;
import com.lambda.appointment.notes.mapper.ConfigEnviromentMapper;
import com.lambda.appointment.notes.mapper.GoogleAuthConfigMapper;

import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigController {

    @Inject
    GoogleAuthConfig googleAuthConfig;

    @Inject
    ConfigEnvironment configEnvironments;

    @Inject
    @ConfigProperty(name = "SQL_SERVER_DB_URL")
    String myProperty;

    @GET
    @Path("google")
    public GoogleAuthConfigDTO getGoogleAuthConfig() {
        GoogleAuthConfigDTO googleAuthConfigDTO = GoogleAuthConfigMapper.toDTO(googleAuthConfig);
        return googleAuthConfigDTO;
    }

    @GET
    @Path("environments")
    public ConfigEnvironmentDTO getLocalEnvironments() {
        ConfigEnvironmentDTO configEnvironmentDTO = ConfigEnviromentMapper.toDTO(configEnvironments);
        return configEnvironmentDTO;
    }

}
