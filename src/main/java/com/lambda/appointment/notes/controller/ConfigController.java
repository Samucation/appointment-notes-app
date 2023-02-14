package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.config.GoogleAuthConfig;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigController {

    @Inject
    GoogleAuthConfig googleAuthConfig;

    @GET
    @Path("/google/redirectUri")
    public Response getRedirectUri() {
        return Response.ok(googleAuthConfig.getRedirectUri()).build();
    }

    @GET
    @Path("/google/clientSecret")
    public Response getClientSecret() {
        return Response.ok(googleAuthConfig.getClientSecret()).build();
    }

    @GET
    @Path("/google/googleAuthUrl")
    public Response getGoogleAuthUrl() {
        return Response.ok(googleAuthConfig.getGoogleAuthApplicationUrl()).build();
    }

    @GET
    @Path("/google/clientId")
    public Response getClientId() {
        return Response.ok(googleAuthConfig.getClientId()).build();
    }

}
