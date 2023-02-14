package com.lambda.appointment.notes.controller;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class GoogleAuthController {

    @Inject
    GoogleAuthResponseGenerator googleAuthResponseGenerator;


    @GET
    @Path("/google")
    public Response redirectToGoogleAuth() {
        return Response.ok(googleAuthResponseGenerator.getGoogleAuthUrl()).build();
    }

    @GET
    @Path("/google/code")
    public Response getGoogleUserCode(@QueryParam("urlWithCode") String urlWithCode) {
        return Response.ok(googleAuthResponseGenerator.extractCodeFromRedirectUrl(urlWithCode)).build();
    }

    @POST
    @Path("/google/callback")
    public Response googleCallback(@QueryParam("code") String loginAuthenticateCode) {
        return Response.ok(googleAuthResponseGenerator.loginGoogleAccess(loginAuthenticateCode)).build();
    }

    @POST
    @Path("/google/user")
    public Response getGoogleUserParams(@QueryParam("accessToken") String accessToken) {
        return Response.ok(googleAuthResponseGenerator.getGoogleUserParams(accessToken)).build();
    }

    @POST
    @Path("/google/valid-token")
    public Response isTokenValid(@QueryParam("accessToken") String accessToken) {
        return Response.ok(googleAuthResponseGenerator.isTokenValid(accessToken)).build();
    }


}
