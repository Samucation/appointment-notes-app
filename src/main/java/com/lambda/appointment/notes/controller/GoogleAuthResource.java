package com.lambda.appointment.notes.controller;


import com.lambda.appointment.notes.service.GoogleAuthService;
import com.lambda.appointment.notes.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class GoogleAuthResource {

    @Inject
    GoogleAuthService googleAuthService;

    @Inject
    LoginService loginService;

    @GET
    @Path("/google")
    public Response redirectToGoogleAuth() {
        // Gerar o URL de autenticação do Google e redirecionar o usuário para ele
        String redirectUrl = googleAuthService.getGoogleAuthUrl();
        return Response.seeOther(URI.create(redirectUrl)).build();
    }

    @GET
    @Path("/google/code")
    public Response getGoogleUserCode(@QueryParam("urlWithCode") String urlWithCode) throws UnsupportedEncodingException {
        String code = googleAuthService.extractCodeFromRedirectUrl(urlWithCode);
        return Response.ok(code).build();
    }

    @POST
    @Path("/google/callback")
    public Response googleCallback(@QueryParam("code") String code) {
        return Response.ok(loginService.loginGoogleAccess(code)).build();
    }

}
