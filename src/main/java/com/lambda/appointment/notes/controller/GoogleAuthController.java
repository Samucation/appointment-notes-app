package com.lambda.appointment.notes.controller;


import com.lambda.appointment.notes.dto.response.CodeExtractedFromGoogleUrlResponse;
import com.lambda.appointment.notes.dto.response.GetGoogleAuthUrlResponse;
import com.lambda.appointment.notes.dto.response.GoogleUserIdResponse;
import com.lambda.appointment.notes.dto.response.IsGoogleTokenValidResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/auth/google")
@Produces(MediaType.APPLICATION_JSON)
public class GoogleAuthController {

    @Inject
    GoogleAuthResponseGenerator googleAuthResponseGenerator;


    @GET
    @Path("/login-url")
    public GetGoogleAuthUrlResponse redirectToGoogleAuth() {
        return googleAuthResponseGenerator.getGoogleAuthUrl();
    }

    @GET
    @Path("extract-url-code")
    public CodeExtractedFromGoogleUrlResponse getGoogleUserCode(@QueryParam("urlWithCode") String urlWithCode) {
        return googleAuthResponseGenerator.extractCodeFromRedirectUrl(urlWithCode);
    }

    @POST
    @Path("generate-token")
    public Map<String, Object> googleCallback(@QueryParam("code") String loginAuthenticateCode) {
        return googleAuthResponseGenerator.loginGoogleAccess(loginAuthenticateCode);
    }

    @POST
    @Path("user-values")
    public GoogleUserIdResponse getGoogleUserParams(@QueryParam("accessToken") String accessToken) {
        return googleAuthResponseGenerator.getGoogleUserParams(accessToken);
    }

    @POST
    @Path("token-is-valid")
    public IsGoogleTokenValidResponse isTokenValid(@QueryParam("accessToken") String accessToken) {
        return googleAuthResponseGenerator.isTokenValid(accessToken);
    }


}
