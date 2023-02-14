package com.lambda.appointment.notes.controller;


import com.lambda.appointment.notes.dto.response.CodeExtractedFromGoogleUrlResponse;
import com.lambda.appointment.notes.service.GoogleAuthService;
import com.lambda.appointment.notes.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class GoogleAuthController {

    @Inject
    GoogleAuthService googleAuthService;

    @Inject
    LoginService loginService;

    @GET
    @Path("/google")
    public Response redirectToGoogleAuth() {
        String redirectUrl = googleAuthService.getGoogleAuthUrl();
        return Response.seeOther(URI.create(redirectUrl)).build();
    }

    @GET
    @Path("/google/code")
    public Response getGoogleUserCode(@QueryParam("urlWithCode") String urlWithCode) {
        CodeExtractedFromGoogleUrlResponse googleAuthUrlCodeResponse = googleAuthService.extractCodeFromRedirectUrl(urlWithCode);
        return Response.ok(googleAuthUrlCodeResponse).build();
    }

    @POST
    @Path("/google/callback")
    public Response googleCallback(@QueryParam("code") String loginAuthenticateCode) {
        return Response.ok(loginService.loginGoogleAccess(loginAuthenticateCode)).build();
    }

    @POST
    @Path("/google/user")
    public Response getGoogleUserParams(@QueryParam("accessToken") String accessToken) {
        return Response.ok(googleAuthService.getGoogleUserParams(accessToken)).build();
    }

    @POST
    @Path("/google/valid-token")
    public Response isTokenValid(@QueryParam("accessToken") String accessToken) {
        return Response.ok(googleAuthService.isTokenValid(accessToken)).build();
    }


}
